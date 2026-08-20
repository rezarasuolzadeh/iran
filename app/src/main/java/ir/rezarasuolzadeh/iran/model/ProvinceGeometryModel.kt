package ir.rezarasuolzadeh.iran.model

import android.graphics.Region
import androidx.compose.ui.graphics.Path

data class ProvinceGeometryModel(
    val province: ProvinceInfoModel,
    val drawPath: Path,
    val hitRegion: Region
)
