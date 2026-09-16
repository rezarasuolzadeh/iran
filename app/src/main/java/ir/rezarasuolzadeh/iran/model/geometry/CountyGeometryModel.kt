package ir.rezarasuolzadeh.iran.model.geometry

import android.graphics.Region
import androidx.compose.ui.graphics.Path
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel

data class CountyGeometryModel(
    val county: CountyInfoModel,
    val drawPath: Path,
    val hitRegion: Region
)