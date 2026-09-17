package ir.rezarasuolzadeh.iran.components.map

import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.Region
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.PathParser
import ir.rezarasuolzadeh.iran.utils.getCounties
import ir.rezarasuolzadeh.iran.utils.getProvinceInfo
import ir.rezarasuolzadeh.iran.model.geometry.CountyGeometryModel
import ir.rezarasuolzadeh.iran.model.geometry.GeometryModel
import ir.rezarasuolzadeh.iran.ui.theme.MapInnerBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapDefaultColor
import ir.rezarasuolzadeh.iran.ui.theme.MapOuterBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapSelectedColor
import ir.rezarasuolzadeh.iran.ui.theme.MapWaterColor

@Composable
fun CountiesMap(
    provinceId: String,
    selectedCityId: String?,
    selectedCityName: (String?) -> Unit,
    onCitySelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
    defaultColor: Color = MapDefaultColor,
    selectedColor: Color = MapSelectedColor,
    nonSelectableColor: Color = MapWaterColor,
    strokeColor: Color = MapInnerBorderColor,
    provinceBorderColor: Color = MapOuterBorderColor
) {
    val province = remember(provinceId) {
        getProvinceInfo(provinceId = provinceId)
    } ?: return

    val cities = remember(provinceId) {
        getCounties(provinceId = provinceId)
    }

    val rawBorderPath = remember(provinceId) {
        PathParser.createPathFromPathData(province.borderPathData)
    }

    val rawCityPaths = remember(provinceId) {
        cities.map { city -> city to PathParser.createPathFromPathData(city.pathData) }
    }

    var canvasSize by remember(provinceId) {
        mutableStateOf(IntSize.Zero)
    }

    val geometries = remember(provinceId, canvasSize) {
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
        val border = android.graphics.Path(rawBorderPath).apply { transform(matrix) }
        val cityGeoms = rawCityPaths.map { (city, rawPath) ->
            val transformed = android.graphics.Path(rawPath).apply { transform(matrix) }
            val bounds = RectF()
            transformed.computeBounds(bounds, true)
            val region = Region().apply {
                setPath(
                    transformed,
                    Region(
                        bounds.left.toInt(),
                        bounds.top.toInt(),
                        bounds.right.toInt() + 1,
                        bounds.bottom.toInt() + 1
                    )
                )
            }
            CountyGeometryModel(county = city, drawPath = transformed.asComposePath(), hitRegion = region)
        }
        GeometryModel(border = border.asComposePath(), cityGeometries = cityGeoms)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(province.width / province.height)
            .onSizeChanged { canvasSize = it }
            .pointerInput(geometries) {
                val geoms = geometries ?: return@pointerInput
                detectTapGestures { offset ->
                    val tapped = geoms.cityGeometries.firstOrNull { geometry ->
                        geometry.county.isSelectable &&
                                geometry.hitRegion.contains(offset.x.toInt(), offset.y.toInt())
                    } ?: return@detectTapGestures

                    selectedCityName(
                        if (tapped.county.id == selectedCityId) null else tapped.county.nameFa
                    )

                    onCitySelected(
                        if (tapped.county.id == selectedCityId) null else tapped.county.id
                    )
                }
            }
    ) {
        val geoms = geometries ?: return@Canvas

        geoms.cityGeometries.forEach { geometry ->
            val fillColor = when {
                !geometry.county.isSelectable -> nonSelectableColor
                geometry.county.id == selectedCityId -> selectedColor
                else -> defaultColor
            }
            drawPath(path = geometry.drawPath, color = fillColor)
            drawPath(path = geometry.drawPath, color = strokeColor, style = Stroke(width = 1.5f))
        }
        drawPath(path = geoms.border, color = provinceBorderColor, style = Stroke(width = 3f))
    }
}