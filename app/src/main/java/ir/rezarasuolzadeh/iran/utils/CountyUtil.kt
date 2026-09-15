package ir.rezarasuolzadeh.iran.utils

import ir.rezarasuolzadeh.iran.constant.province.Iran
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel

fun getCounties(provinceId: String): List<CountyInfoModel> = Iran.filter { it.provinceId == provinceId }