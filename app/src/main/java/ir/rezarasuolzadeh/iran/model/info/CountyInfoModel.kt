package ir.rezarasuolzadeh.iran.model.info

data class CountyInfoModel(
    val id: String,
    val provinceId: String,
    val nameFa: String,
    val pathData: String,
    val isSelectable: Boolean
)