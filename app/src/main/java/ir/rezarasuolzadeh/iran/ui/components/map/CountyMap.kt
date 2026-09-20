package ir.rezarasuolzadeh.iran.ui.components.map

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.PathParser
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel
import ir.rezarasuolzadeh.iran.ui.theme.MapOuterBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapSelectedColor
import ir.rezarasuolzadeh.iran.utils.map.getCounties
import androidx.compose.ui.graphics.Path as ComposePath

/**
 * Parses [CountyInfoModel.pathData] — the raw SVG path string describing this county's
 * outline — into an [android.graphics.Path].
 *
 * This is deliberately kept separate from scaling/transforming: the parsed path is the
 * county's shape in its *original* SVG coordinate space (the same space the whole map's
 * `pathData` values were authored in), and is reused as-is every time the available
 * canvas size changes, avoiding a redundant re-parse of the path string on every layout pass.
 *
 * [remember] is keyed on [CountyInfoModel.id] so the parse only reruns when the caller
 * switches to a *different* county, not on every recomposition.
 *
 * @param county the county whose outline should be parsed.
 * @return the county's outline as an unscaled, untranslated [android.graphics.Path].
 */
@Composable
private fun rememberRawCountyPath(county: CountyInfoModel): Path = remember(county.id) {
    PathParser.createPathFromPathData(county.pathData)
}

/**
 * Computes the axis-aligned bounding box of [rawPath], in the same coordinate space the
 * path itself is defined in.
 *
 * Unlike [ProvinceMap], which can read a province's bounds directly off its model
 * ([ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel] carries `minX`/`minY`/`width`/`height`),
 * [CountyInfoModel] has no such precomputed bounds — a single county's extent is only
 * known by measuring its actual path. This result is what [rememberScaledGeometry] uses
 * to know how much to translate and scale the path to fit the canvas.
 *
 * @param rawPath the unscaled county outline, as returned by [rememberRawCountyPath].
 * @return the tight bounding rectangle of [rawPath].
 */
@Composable
private fun rememberCountyBounds(rawPath: Path): RectF = remember(rawPath) {
    RectF().apply { rawPath.computeBounds(this, true) }
}

/**
 * Fits [rawPath] into the current canvas size and returns it ready to draw.
 *
 * The transform does two things, applied in order via [Matrix]:
 * 1. **Translate** the path so its bounding box's top-left corner sits at the origin,
 *    then nudge it inward by [padding] (as a fraction of the box's own size) on every
 *    side, so the county's outline doesn't touch the very edge of the canvas.
 * 2. **Scale** uniformly (same factor on both axes, to avoid warping the shape) so the
 *    padded bounding box exactly fills the canvas width. Because [Canvas] is constrained
 *    to the same aspect ratio as [bounds] (see the `aspectRatio` modifier in [CountyMap]),
 *    fitting the width is enough to also fit the height.
 *
 * Returns `null` until the canvas has actually been measured (width/height of `0` means
 * layout hasn't happened yet), so callers must handle the "not ready" case rather than
 * assuming a geometry is always available.
 *
 * @param rawPath the unscaled county outline.
 * @param bounds the bounding box of [rawPath], used to compute the fit.
 * @param canvasSize the current pixel size of the [Canvas] this will be drawn into.
 * @return the county outline transformed into canvas space, or `null` if [canvasSize]
 * isn't known yet.
 */
@Composable
private fun rememberScaledGeometry(
    rawPath: Path,
    bounds: RectF,
    canvasSize: IntSize
): ComposePath? = remember(rawPath, canvasSize) {
    if (canvasSize.width == 0 || canvasSize.height == 0) {
        return@remember null
    }

    val padding = 0.04f
    val scale = canvasSize.width / (bounds.width() * (1 + 2 * padding))
    val matrix = Matrix().apply {
        setTranslate(
            -bounds.left + bounds.width() * padding,
            -bounds.top + bounds.height() * padding
        )
        postScale(scale, scale)
    }

    Path(rawPath).apply { transform(matrix) }.asComposePath()
}

/**
 * Draws a single county on its own, filling all the available space with just that
 * county's outline — no siblings, no province border, and no tap handling.
 *
 * This is the last stop in the map drill-down: [IranMap] lets the user pick a province,
 * [ProvinceMap] lets them pick a county within it, and this composable is what renders
 * that one chosen county by itself (for example, as a small preview or confirmation view).
 * Because only one shape is ever shown and nothing here is selectable, there's no hit
 * testing and no `Region` — that machinery only exists in [IranMap] and [ProvinceMap],
 * where the user taps between multiple shapes.
 *
 * If [countyId] doesn't resolve to a real county under [provinceId], this composable
 * renders nothing.
 *
 * @param provinceId the id of the province [countyId] belongs to.
 * @param countyId the id of the single county to render.
 * @param defaultColor the fill color for the county's shape.
 * @param borderColor the stroke color drawn around the county's outline.
 */
@Composable
fun CountyMap(
    modifier: Modifier = Modifier,
    provinceId: String,
    countyId: String,
    defaultColor: Color = MapSelectedColor,
    borderColor: Color = MapOuterBorderColor
) {
    val county = remember(provinceId, countyId) { getCounties(provinceId = provinceId).firstOrNull { it.id == countyId } } ?: return
    val rawCountyPath = rememberRawCountyPath(county = county)
    val bounds = rememberCountyBounds(rawPath = rawCountyPath)
    var canvasSize by remember(countyId) { mutableStateOf(value = IntSize.Zero) }
    val geometry = rememberScaledGeometry(
        rawPath = rawCountyPath,
        bounds = bounds,
        canvasSize = canvasSize
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(bounds.width() / bounds.height())
            .onSizeChanged { canvasSize = it }
    ) {
        val path = geometry ?: return@Canvas

        drawPath(
            path = path,
            color = defaultColor
        )
        drawPath(
            path = path,
            color = borderColor,
            style = Stroke(width = 3f)
        )
    }
}