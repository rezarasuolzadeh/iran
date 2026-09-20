package ir.rezarasuolzadeh.iran.model.info

/**
 * A county's raw data: which province it belongs to, its name and seat, its map outline,
 * and whether it's a real, tappable county.
 */
data class CountyInfoModel(
    val id: String,
    val provinceId: String,
    val name: String,
    val countySeatName: String,
    val pathData: String,
    val isSelectable: Boolean
)