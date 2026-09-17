package ir.rezarasuolzadeh.iran.components.map

import android.graphics.Matrix
import android.graphics.Path
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
import ir.rezarasuolzadeh.iran.model.geometry.ProvinceGeometryModel
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel
import ir.rezarasuolzadeh.iran.ui.theme.MapDefaultColor
import ir.rezarasuolzadeh.iran.ui.theme.MapInnerBorderColor
import ir.rezarasuolzadeh.iran.ui.theme.MapSelectedColor
import ir.rezarasuolzadeh.iran.ui.theme.MapWaterColor

@Composable
private fun rememberRawProvincePaths(): List<Pair<ProvinceInfoModel, Path>> = remember {
    iranProvinces.map { province -> province to PathParser.createPathFromPathData(province.pathData) }
}

@Composable
private fun rememberScaledGeometries(
    rawPaths: List<Pair<ProvinceInfoModel, Path>>,
    canvasSize: IntSize,
): List<ProvinceGeometryModel> = remember(canvasSize, rawPaths) {
    if (canvasSize.width == 0 || canvasSize.height == 0) {
        return@remember emptyList()
    }
    val scale = canvasSize.width / IRAN_MAP_VIEW_BOX_WIDTH
    val matrix = Matrix().apply {
        setTranslate(-IRAN_MAP_VIEW_BOX_MIN_X, -IRAN_MAP_VIEW_BOX_MIN_Y)
        postScale(scale, scale)
    }
    rawPaths.map { (province, androidPath) ->
        val transformed = Path(androidPath).apply { transform(matrix) }
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
        ProvinceGeometryModel(
            province = province,
            drawPath = transformed.asComposePath(),
            hitRegion = region,
        )
    }
}

@Composable
fun ProvincesMap(
    selectedProvinceId: String?,
    selectedProvinceName: (String?) -> Unit,
    onProvinceSelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
    defaultColor: Color = MapDefaultColor,
    selectedColor: Color = MapSelectedColor,
    waterColor: Color = MapWaterColor,
    strokeColor: Color = MapInnerBorderColor
) {
    val rawPaths = rememberRawProvincePaths()
    var canvasSize by remember { mutableStateOf(value = IntSize.Zero) }
    val geometries = rememberScaledGeometries(rawPaths, canvasSize)

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

                    selectedProvinceName(
                        if (tapped.province.id == selectedProvinceId) null else tapped.province.nameFa
                    )

                    onProvinceSelected(
                        if (tapped.province.id == selectedProvinceId) null else tapped.province.id
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
                color = strokeColor,
                style = Stroke(width = 1.5f)
            )
        }
    }
}