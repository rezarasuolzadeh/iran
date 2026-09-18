package ir.rezarasuolzadeh.iran.model.info

data class CountyInfoModel(
    val id: String,
    val provinceId: String,
    val name: String,
    val countySeatName: String,
    val pathData: String,
    val isSelectable: Boolean
)