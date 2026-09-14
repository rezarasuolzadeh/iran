package ir.rezarasuolzadeh.iran.utils

import ir.rezarasuolzadeh.iran.constant.iranCounties
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel

fun getCounties(provinceId: String): List<CountyInfoModel> = iranCounties.filter { it.provinceId == provinceId }