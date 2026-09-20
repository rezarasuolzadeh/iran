package ir.rezarasuolzadeh.iran.model.geometry

import android.graphics.Region
import androidx.compose.ui.graphics.Path
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel

/**
 * A province's outline after it's been scaled to fit the canvas — ready to draw ([drawPath])
 * and ready to tap-test against ([hitRegion]).
 */
data class ProvinceGeometryModel(
    val province: ProvinceInfoModel,
    val drawPath: Path,
    val hitRegion: Region
)