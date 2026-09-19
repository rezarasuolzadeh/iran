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

@Composable
private fun rememberRawCountyPath(county: CountyInfoModel): Path = remember(county.id) {
    PathParser.createPathFromPathData(county.pathData)
}

@Composable
private fun rememberCountyBounds(rawPath: Path): RectF = remember(rawPath) {
    RectF().apply { rawPath.computeBounds(this, true) }
}

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