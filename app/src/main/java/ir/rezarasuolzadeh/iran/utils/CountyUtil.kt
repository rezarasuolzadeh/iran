package ir.rezarasuolzadeh.iran.utils

import ir.rezarasuolzadeh.iran.constant.iran.iranCounties
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel

fun getCounties(provinceId: String): List<CountyInfoModel> = iranCounties.filter { it.provinceId == provinceId }

fun getCountyName(countyId: String): String? = iranCounties.firstOrNull { it.id == countyId }?.name

fun getCountySeatName(countyId: String): String? = iranCounties.firstOrNull { it.id == countyId }?.countySeatName