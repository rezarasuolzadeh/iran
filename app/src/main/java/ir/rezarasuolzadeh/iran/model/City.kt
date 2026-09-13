package ir.rezarasuolzadeh.iran.model

data class City(
    val id: String,
    val provinceId: String,
    val nameFa: String,
    val pathData: String,
    val isSelectable: Boolean
)