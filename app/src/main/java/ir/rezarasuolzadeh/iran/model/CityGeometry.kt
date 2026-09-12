package ir.rezarasuolzadeh.iran.model

import android.graphics.Region
import androidx.compose.ui.graphics.Path

data class CityGeometry(
    val city: City,
    val drawPath: Path,
    val hitRegion: Region,
)