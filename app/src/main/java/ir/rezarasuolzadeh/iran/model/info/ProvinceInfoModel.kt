package ir.rezarasuolzadeh.iran.model.info

/**
 * A province's raw data: name, both map outlines (`pathData` for the whole-country view,
 * `borderPathData` + bounds for a zoomed-in view), and whether it's a real, tappable province.
 */
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