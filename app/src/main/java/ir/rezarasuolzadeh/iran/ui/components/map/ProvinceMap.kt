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
import ir.rezarasuolzadeh.iran.utils.extensions.toHitRegion
import ir.rezarasuolzadeh.iran.model.geometry.CountyGeometryModel
import ir.rezarasuolzadeh.iran.model.geometry.GeometryModel
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel
import ir.rezarasuolzadeh.iran.ui.theme.MapDefaultColor
import ir.rezarasuolzadeh.iran.ui.theme.MapInnerBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapOuterBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapSelectedColor
import ir.rezarasuolzadeh.iran.ui.theme.MapWaterColor
import ir.rezarasuolzadeh.iran.utils.map.getCounties
import ir.rezarasuolzadeh.iran.utils.map.getProvinceInfo

/**
 * Parses [ProvinceInfoModel.borderPathData] — the province's own outline, separate from
 * its counties' outlines — into an [android.graphics.Path].
 *
 * This border is drawn as a visual frame around the whole province (see the final
 * `drawPath` call in [ProvinceMap]) so the counties read as pieces of one region rather
 * than a loose scatter of shapes. It shares [ProvinceInfoModel]'s own coordinate space —
 * the same space `minX`/`minY`/`width`/`height` describe — which is why
 * [rememberScaledGeometries] can transform it with the exact same [Matrix] it uses for
 * the counties.
 *
 * @param province the province whose border should be parsed.
 * @return the province's border as an unscaled, untranslated [android.graphics.Path].
 */
@Composable
private fun rememberRawBorderPath(province: ProvinceInfoModel): Path = remember(province.id) {
    PathParser.createPathFromPathData(province.borderPathData)
}

/**
 * Parses every county belonging to [provinceId] into an [android.graphics.Path], keeping
 * each one paired with its source model.
 *
 * Looked up fresh via [getCounties] and re-parsed whenever [provinceId] changes — there's
 * no [ProvinceInfoModel] argument here (unlike [rememberRawBorderPath]) because the id is
 * enough to load the right set of counties, and using it as the sole [remember] key keeps
 * this independent of whichever [ProvinceInfoModel] instance the caller happens to hold.
 *
 * @param provinceId the id of the province whose counties should be parsed.
 * @return each county paired with its outline, unscaled and in the province's own
 * coordinate space.
 */
@Composable
private fun rememberRawCountyPaths(provinceId: String): List<Pair<CountyInfoModel, Path>> = remember(provinceId) {
    getCounties(provinceId = provinceId).map { county ->
        county to PathParser.createPathFromPathData(county.pathData)
    }
}

/**
 * Fits [province]'s border and all of its counties into the current canvas size, as one
 * consistent transform, and packages the result into a [GeometryModel] ready for drawing
 * and tap detection.
 *
 * Because every shape here — the border and every county — was authored in the same
 * per-province coordinate space, a single [Matrix] fits all of them at once:
 * 1. **Translate** [province]'s bounding box (`minX`, `minY`, `width`, `height`, already
 *    known ahead of time from the model — unlike [CountyMap], which has to measure a
 *    lone county's bounds itself) so its top-left corner sits at the origin, then nudge
 *    inward by [padding] on every side so the province doesn't touch the canvas edge.
 * 2. **Scale** uniformly so the padded box exactly fills the canvas width. [ProvinceMap]
 *    constrains its `Canvas` to `province.width / province.height`, so fitting the width
 *    is enough to fit the height too.
 *
 * Each transformed county outline is converted to a
 * [ir.rezarasuolzadeh.iran.utils.extensions.toHitRegion] [android.graphics.Region], which
 * is what lets [ProvinceMap] later resolve "did the user's tap land inside county X?" for
 * each county independently, in constant time.
 *
 * Returns `null` until the canvas has been measured, since there's no meaningful size to
 * fit the shapes to yet.
 *
 * @param province the province whose border and counties are being laid out; also the
 * source of the bounding box the transform is built from.
 * @param rawBorderPath the province's unscaled border, as returned by [rememberRawBorderPath].
 * @param rawCountyPaths every county's unscaled outline, as returned by [rememberRawCountyPaths].
 * @param canvasSize the current pixel size of the [Canvas] this will be drawn into.
 * @return the province's border plus every county's geometry, all in canvas coordinates,
 * or `null` if [canvasSize] isn't known yet.
 */
@Composable
private fun rememberScaledGeometries(
    province: ProvinceInfoModel,
    rawBorderPath: Path,
    rawCountyPaths: List<Pair<CountyInfoModel, Path>>,
    canvasSize: IntSize
): GeometryModel? = remember(province.id, canvasSize) {
    if (canvasSize.width == 0 || canvasSize.height == 0) {
        return@remember null
    }

    val padding = 0.04f
    val scale = canvasSize.width / (province.width * (1 + 2 * padding))
    val matrix = Matrix().apply {
        setTranslate(
            -province.minX + province.width * padding,
            -province.minY + province.height * padding
        )
        postScale(scale, scale)
    }

    val border = Path(rawBorderPath).apply { transform(matrix) }
    val countyGeometries = rawCountyPaths.map { (county, rawPath) ->
        val transformed = Path(rawPath).apply { transform(matrix) }
        CountyGeometryModel(
            county = county,
            drawPath = transformed.asComposePath(),
            hitRegion = transformed.toHitRegion()
        )
    }

    GeometryModel(
        border = border.asComposePath(),
        cityGeometries = countyGeometries
    )
}

/**
 * Draws a single province zoomed in to fill the available space, with all of its counties
 * shown individually and tappable, so the user can select one.
 *
 * This is the middle step of the map drill-down: it follows [IranMap], where the user
 * picks which province to zoom into, and typically precedes [CountyMap], which shows
 * whichever single county is chosen here on its own.
 *
 * Selection is single-choice and toggles off on a second tap: tapping the already-selected
 * county deselects it (all three `onCounty*Selected` callbacks fire with `null`), and
 * tapping a different one both deselects the old county and selects the new one in a
 * single gesture — callers don't need to manage that exclusivity themselves. A county
 * whose [CountyInfoModel.isSelectable] is `false` (used for non-administrative areas drawn
 * on the map, such as bodies of water) is drawn but ignores taps entirely.
 *
 * The three `onCounty*Selected` callbacks all fire together, once per tap, each carrying
 * the same selection in a different shape (id, display name, and the full model) — pick
 * whichever is most convenient for the caller rather than deriving one from another.
 *
 * If [provinceId] doesn't resolve to a real province, this composable renders nothing.
 *
 * @param provinceId the id of the province to zoom into and show counties for.
 * @param selectedCountyId the id of the county that should currently be drawn as selected,
 * or `null` if none is selected. This composable is stateless with respect to selection —
 * the caller owns this value (typically keyed per province, so switching provinces and
 * coming back preserves the earlier pick) and is expected to update it from
 * [onCountyIdSelected].
 * @param defaultColor the fill color for an unselected, selectable county.
 * @param selectedColor the fill color for the currently selected county.
 * @param waterColor the fill color for a non-selectable county (see [CountyInfoModel.isSelectable]).
 * @param innerBorderColor the stroke color drawn along each individual county's outline.
 * @param outerBorderColor the stroke color drawn along the province's own outer border.
 * @param onCountyIdSelected called with the tapped county's id, or `null` when a selection
 * is cleared.
 * @param onCountyNameSelected called with the tapped county's display name, or `null` when
 * a selection is cleared.
 * @param onCountyInfoSelected called with the tapped county's full [CountyInfoModel], or
 * `null` when a selection is cleared.
 */
@Composable
fun ProvinceMap(
    modifier: Modifier = Modifier,
    provinceId: String,
    selectedCountyId: String?,
    defaultColor: Color = MapDefaultColor,
    selectedColor: Color = MapSelectedColor,
    innerBorderColor: Color = MapInnerBorderColor,
    outerBorderColor: Color = MapOuterBorderColor,
    onCountyIdSelected: (countyId: String?) -> Unit = {},
    onCountyNameSelected: (countyName: String?) -> Unit = {},
    onCountyInfoSelected: (countyInfo: CountyInfoModel?) -> Unit = {}
) {
    val province = remember(provinceId) { getProvinceInfo(provinceId = provinceId) } ?: return
    val rawBorderPath = rememberRawBorderPath(province = province)
    val rawCountyPaths = rememberRawCountyPaths(provinceId = provinceId)
    var canvasSize by remember(provinceId) { mutableStateOf(value = IntSize.Zero) }
    val geometries = rememberScaledGeometries(
        province = province,
        rawBorderPath = rawBorderPath,
        rawCountyPaths = rawCountyPaths,
        canvasSize = canvasSize
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(province.width / province.height)
            .onSizeChanged { canvasSize = it }
            .pointerInput(geometries) {
                val geoms = geometries ?: return@pointerInput
                detectTapGestures { offset ->
                    val tapped = geoms.cityGeometries.firstOrNull { geometry ->
                        geometry.county.isSelectable && geometry.hitRegion.contains(offset.x.toInt(), offset.y.toInt())
                    } ?: return@detectTapGestures

                    onCountyIdSelected(
                        if (tapped.county.id == selectedCountyId) null else tapped.county.id
                    )

                    onCountyNameSelected(
                        if (tapped.county.id == selectedCountyId) null else tapped.county.name
                    )

                    onCountyInfoSelected(
                        if (tapped.county.id == selectedCountyId) null else tapped.county
                    )
                }
            }
    ) {
        val geoms = geometries ?: return@Canvas

        geoms.cityGeometries.forEach { geometry ->
            val fillColor = when {
                geometry.county.id == selectedCountyId -> selectedColor
                else -> defaultColor
            }
            drawPath(
                path = geometry.drawPath,
                color = fillColor
            )
            drawPath(
                path = geometry.drawPath,
                color = innerBorderColor,
                style = Stroke(width = 1.5f)
            )
        }
        drawPath(
            path = geoms.border,
            color = outerBorderColor,
            style = Stroke(width = 3f)
        )
    }
}