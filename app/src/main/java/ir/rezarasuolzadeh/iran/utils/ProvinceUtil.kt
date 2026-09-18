package ir.rezarasuolzadeh.iran.utils

import ir.rezarasuolzadeh.iran.constant.iran.iranProvinces
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel

fun getProvinceInfo(provinceId: String): ProvinceInfoModel? = iranProvinces.firstOrNull { it.id == provinceId }

fun getProvinceName(provinceId: String): String? = iranProvinces.firstOrNull { it.id == provinceId }?.name