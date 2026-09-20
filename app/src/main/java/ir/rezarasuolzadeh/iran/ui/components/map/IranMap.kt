package ir.rezarasuolzadeh.iran.ui.components.map

import android.graphics.Matrix
import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.PathParser
import ir.rezarasuolzadeh.iran.constant.Constants.IRAN_MAP_VIEW_BOX_HEIGHT
import ir.rezarasuolzadeh.iran.constant.Constants.IRAN_MAP_VIEW_BOX_MIN_X
import ir.rezarasuolzadeh.iran.constant.Constants.IRAN_MAP_VIEW_BOX_MIN_Y
import ir.rezarasuolzadeh.iran.constant.Constants.IRAN_MAP_VIEW_BOX_WIDTH
import ir.rezarasuolzadeh.iran.constant.iran.iranProvinces
import ir.rezarasuolzadeh.iran.utils.extensions.toHitRegion
import ir.rezarasuolzadeh.iran.model.geometry.ProvinceGeometryModel
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel
import ir.rezarasuolzadeh.iran.ui.theme.MapDefaultColor
import ir.rezarasuolzadeh.iran.ui.theme.MapInnerBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapSelectedColor
import ir.rezarasuolzadeh.iran.ui.theme.MapWaterColor

/**
 * Parses every province's [ProvinceInfoModel.pathData] into an [android.graphics.Path],
 * once, and keeps them paired with their source model.
 *
 * All 31 provinces share one coordinate space — the "Iran map" view box defined by
 * [IRAN_MAP_VIEW_BOX_WIDTH]/[IRAN_MAP_VIEW_BOX_HEIGHT] — so unlike [ProvinceMap] or
 * [CountyMap], there's nothing province-specific to key this [remember] on: it's computed
 * exactly once for the composable's whole lifetime and reused across every recomposition
 * and every canvas resize.
 *
 * @return each province paired with its outline, unscaled and in view-box coordinates.
 */
@Composable
private fun rememberRawProvincePaths(): List<Pair<ProvinceInfoModel, Path>> = remember {
    iranProvinces.map { province ->
        province to PathParser.createPathFromPathData(province.pathData)
    }
}

/**
 * Fits every province's raw outline into the current canvas size, and packages each one
 * into a [ProvinceGeometryModel] ready for both drawing and tap detection.
 *
 * Because all provinces already live in one shared view box, this is a single uniform
 * transform applied identically to every shape — unlike [CountyMap]'s per-shape fit, this
 * doesn't need to measure anything first: it translates the whole view box's origin to
 * `(0, 0)` and scales by `canvasSize.width / IRAN_MAP_VIEW_BOX_WIDTH`. Since [IranMap]
 * constrains its `Canvas` to the view box's own aspect ratio, fitting the width is enough
 * to fit the height too, so both axes end up scaled by the same factor and nothing gets
 * stretched.
 *
 * Each transformed path is converted to a [ir.rezarasuolzadeh.iran.utils.extensions.toHitRegion]
 * [android.graphics.Region] — this is what lets [IranMap] later ask "did the user's tap
 * land inside province X?" in constant time per shape, instead of hit-testing against the
 * raw Bézier path.
 *
 * Returns an empty list until the canvas has been measured, since there's nothing
 * meaningful to scale to yet.
 *
 * @param rawPaths every province's unscaled outline, as returned by [rememberRawProvincePaths].
 * @param canvasSize the current pixel size of the [Canvas] this will be drawn into.
 * @return one [ProvinceGeometryModel] per province, in canvas coordinates, or an empty
 * list if [canvasSize] isn't known yet.
 */
@Composable
private fun rememberScaledGeometries(
    rawPaths: List<Pair<ProvinceInfoModel, Path>>,
    canvasSize: IntSize
): List<ProvinceGeometryModel> = remember(canvasSize, rawPaths) {
    if (canvasSize.width == 0 || canvasSize.height == 0) {
        return@remember emptyList()
    }

    val scale = canvasSize.width / IRAN_MAP_VIEW_BOX_WIDTH
    val matrix = Matrix().apply {
        setTranslate(-IRAN_MAP_VIEW_BOX_MIN_X, -IRAN_MAP_VIEW_BOX_MIN_Y)
        postScale(scale, scale)
    }

    rawPaths.map { (province, rawPath) ->
        val transformed = Path(rawPath).apply { transform(matrix) }
        ProvinceGeometryModel(
            province = province,
            drawPath = transformed.asComposePath(),
            hitRegion = transformed.toHitRegion()
        )
    }
}

/**
 * Draws the full map of Iran with all 31 provinces, letting the user tap one to select it.
 *
 * This is the first step of the map drill-down: once a province is picked here, its id
 * is typically handed off to [ProvinceMap] to let the user narrow their choice down to a
 * single county within it.
 *
 * Selection is single-choice and toggles off on a second tap: tapping the already-selected
 * province deselects it (all three `onProvince*Selected` callbacks fire with `null`), and
 * tapping a different one both deselects the old province and selects the new one in a
 * single gesture — callers don't need to manage that exclusivity themselves. A province
 * whose [ProvinceInfoModel.isSelectable] is `false` (used for non-administrative regions
 * drawn on the map, such as bodies of water) is drawn but ignores taps entirely.
 *
 * The three `onProvince*Selected` callbacks all fire together, once per tap, each carrying
 * the same selection in a different shape (id, display name, and the full model) — pick
 * whichever is most convenient for the caller rather than deriving one from another.
 *
 * @param selectedProvinceId the id of the province that should currently be drawn as
 * selected, or `null` if none is selected. This composable is stateless with respect to
 * selection — the caller owns this value and is expected to update it from
 * [onProvinceIdSelected].
 * @param defaultColor the fill color for an unselected, selectable province.
 * @param selectedColor the fill color for the currently selected province.
 * @param waterColor the fill color for a non-selectable province (see [ProvinceInfoModel.isSelectable]).
 * @param borderColor the stroke color drawn along every province's outline.
 * @param onProvinceIdSelected called with the tapped province's id, or `null` when a
 * selection is cleared.
 * @param onProvinceNameSelected called with the tapped province's display name, or `null`
 * when a selection is cleared.
 * @param onProvinceInfoSelected called with the tapped province's full [ProvinceInfoModel],
 * or `null` when a selection is cleared.
 */
@Composable
fun IranMap(
    modifier: Modifier = Modifier,
    selectedProvinceId: String?,
    defaultColor: Color = MapDefaultColor,
    selectedColor: Color = MapSelectedColor,
    waterColor: Color = MapWaterColor,
    borderColor: Color = MapInnerBorderColor,
    onProvinceIdSelected: (provinceId: String?) -> Unit = {},
    onProvinceNameSelected: (provinceName: String?) -> Unit = {},
    onProvinceInfoSelected: (provinceInfo: ProvinceInfoModel?) -> Unit = {}
) {
    val rawPaths = rememberRawProvincePaths()
    var canvasSize by remember { mutableStateOf(value = IntSize.Zero) }
    val geometries = rememberScaledGeometries(
        rawPaths = rawPaths,
        canvasSize = canvasSize
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(IRAN_MAP_VIEW_BOX_WIDTH / IRAN_MAP_VIEW_BOX_HEIGHT)
            .onSizeChanged { canvasSize = it }
            .pointerInput(geometries) {
                detectTapGestures { offset ->
                    val tapped = geometries.firstOrNull { geometry ->
                        geometry.province.isSelectable && geometry.hitRegion.contains(offset.x.toInt(), offset.y.toInt())
                    } ?: return@detectTapGestures

                    onProvinceIdSelected(
                        if (tapped.province.id == selectedProvinceId) null else tapped.province.id
                    )

                    onProvinceNameSelected(
                        if (tapped.province.id == selectedProvinceId) null else tapped.province.name
                    )

                    onProvinceInfoSelected(
                        if (tapped.province.id == selectedProvinceId) null else tapped.province
                    )
                }
            }
    ) {
        geometries.forEach { geometry ->
            val fillColor = when {
                !geometry.province.isSelectable -> waterColor
                geometry.province.id == selectedProvinceId -> selectedColor
                else -> defaultColor
            }
            drawPath(
                path = geometry.drawPath,
                color = fillColor
            )
            drawPath(
                path = geometry.drawPath,
                color = borderColor,
                style = Stroke(width = 1.5f)
            )
        }
    }
}