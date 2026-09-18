package ir.rezarasuolzadeh.iran.model.info

data class ProvinceInfoModel(
    val id: String,
    val name: String,
    val isSelectable: Boolean,
    val pathData: String,
    val borderPathData: String,
    val minX: Float,
    val minY: Float,
    val width: Float,
    val height: Float
)