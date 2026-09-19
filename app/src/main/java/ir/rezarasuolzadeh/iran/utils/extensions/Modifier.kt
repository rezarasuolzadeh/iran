package ir.rezarasuolzadeh.iran.utils.extensions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.calculateSizeAccordingToProvince(provinceId: String): Modifier = run {
    return@run when (provinceId) {
        "SistanVaBaluchestan" -> Modifier.size(300.dp)
        "AzerbaijaneGharbi" -> Modifier.size(300.dp)
        "Ardabil" -> Modifier.size(200.dp)
        else -> Modifier.fillMaxSize()
    }
}