package ir.rezarasuolzadeh.iran.model.geometry

import android.graphics.Region
import androidx.compose.ui.graphics.Path
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel

data class ProvinceGeometryModel(
    val province: ProvinceInfoModel,
    val drawPath: Path,
    val hitRegion: Region
)