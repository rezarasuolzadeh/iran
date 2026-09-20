package ir.rezarasuolzadeh.iran.utils.extensions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Returns a size for a province's map view, tuned to that province's shape.
 * Some provinces (e.g. long, narrow, or unusually large ones) need a fixed
 * size to look right; all others simply fill the available space.
 *
 * @param provinceId the unique identifier of the province being displayed.
 */
fun Modifier.calculateSizeAccordingToProvince(provinceId: String): Modifier = run {
    return@run when (provinceId) {
        "SistanVaBaluchestan" -> Modifier.size(300.dp)
        "AzerbaijaneGharbi" -> Modifier.size(300.dp)
        "Ardabil" -> Modifier.size(200.dp)
        else -> Modifier.fillMaxSize()
    }
}