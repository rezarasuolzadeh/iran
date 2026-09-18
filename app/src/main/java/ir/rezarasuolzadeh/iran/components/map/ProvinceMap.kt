package ir.rezarasuolzadeh.iran.components.map

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
import ir.rezarasuolzadeh.iran.extensions.toHitRegion
import ir.rezarasuolzadeh.iran.model.geometry.CountyGeometryModel
import ir.rezarasuolzadeh.iran.model.geometry.GeometryModel
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel
import ir.rezarasuolzadeh.iran.ui.theme.MapDefaultColor
import ir.rezarasuolzadeh.iran.ui.theme.MapInnerBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapOuterBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapSelectedColor
import ir.rezarasuolzadeh.iran.ui.theme.MapWaterColor
import ir.rezarasuolzadeh.iran.utils.getCounties
import ir.rezarasuolzadeh.iran.utils.getProvinceInfo

@Composable
private fun rememberRawBorderPath(province: ProvinceInfoModel): Path = remember(province.id) {
    PathParser.createPathFromPathData(province.borderPathData)
}

@Composable
private fun rememberRawCountyPaths(provinceId: String): List<Pair<CountyInfoModel, Path>> = remember(provinceId) {
    getCounties(provinceId = provinceId).map { county ->
        county to PathParser.createPathFromPathData(county.pathData)
    }
}

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

@Composable
fun ProvinceMap(
    modifier: Modifier = Modifier,
    provinceId: String,
    selectedCountyId: String?,
    defaultColor: Color = MapDefaultColor,
    selectedColor: Color = MapSelectedColor,
    waterColor: Color = MapWaterColor,
    innerBorderColor: Color = MapInnerBorderColor,
    outerBorderColor: Color = MapOuterBorderColor,
    onCountyIdSelected: (String?) -> Unit = {},
    onCountyNameSelected: (String?) -> Unit = {},
    onCountyInfoSelected: (CountyInfoModel?) -> Unit = {}
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
                !geometry.county.isSelectable -> waterColor
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