package ir.rezarasuolzadeh.iran.utils.map

import ir.rezarasuolzadeh.iran.constant.iran.iranCounties
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel

fun getCounties(provinceId: String): List<CountyInfoModel> {
    return iranCounties.filter { it.provinceId == provinceId }
}

fun getCountyName(countyId: String): String? {
    return iranCounties.firstOrNull { it.id == countyId }?.name
}

fun getCountySeatName(countyId: String): String? {
    return iranCounties.firstOrNull { it.id == countyId }?.countySeatName
}

fun getCountyInfo(countyId: String): CountyInfoModel? {
    return iranCounties.firstOrNull { it.id == countyId }
}