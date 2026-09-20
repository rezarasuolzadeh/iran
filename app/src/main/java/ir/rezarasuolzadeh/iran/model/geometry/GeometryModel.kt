package ir.rezarasuolzadeh.iran.model.geometry

import androidx.compose.ui.graphics.Path

/**
 * Everything [ProvinceMap] needs to draw one province: its own outer [border] plus every
 * one of its counties, already scaled and ready to draw ([cityGeometries]).
 */
data class GeometryModel(
    val border: Path,
    val cityGeometries: List<CountyGeometryModel>
)