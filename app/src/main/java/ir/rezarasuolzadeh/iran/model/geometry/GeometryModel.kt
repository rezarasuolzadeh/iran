package ir.rezarasuolzadeh.iran.model.geometry

import androidx.compose.ui.graphics.Path

data class GeometryModel(
    val border: Path,
    val cityGeometries: List<CountyGeometryModel>
)