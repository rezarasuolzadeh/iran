package ir.rezarasuolzadeh.iran.utils

import ir.rezarasuolzadeh.iran.constant.iranProvinces
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel

fun getProvinceInfo(provinceId: String): ProvinceInfoModel? = iranProvinces.firstOrNull { it.id == provinceId }