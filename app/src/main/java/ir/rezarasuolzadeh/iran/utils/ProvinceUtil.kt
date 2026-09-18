package ir.rezarasuolzadeh.iran.utils

import ir.rezarasuolzadeh.iran.constant.iran.iranProvinces
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel

fun getProvinceName(provinceId: String): String? {
    return iranProvinces.firstOrNull { it.id == provinceId }?.name
}

fun getProvinceInfo(provinceId: String): ProvinceInfoModel? {
    return iranProvinces.firstOrNull { it.id == provinceId }
}