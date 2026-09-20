package ir.rezarasuolzadeh.iran.utils.map

import ir.rezarasuolzadeh.iran.constant.iran.iranProvinces
import ir.rezarasuolzadeh.iran.model.info.ProvinceInfoModel

/**
 * Returns the display name of a province, or `null` if [provinceId] doesn't match any province.
 *
 * @param provinceId the unique identifier of the province.
 */
fun getProvinceName(provinceId: String): String? {
    return iranProvinces.firstOrNull { it.id == provinceId }?.name
}

/**
 * Returns full info for a province — including its map path and display name —
 * or `null` if [provinceId] doesn't match any province.
 *
 * @param provinceId the unique identifier of the province.
 */
fun getProvinceInfo(provinceId: String): ProvinceInfoModel? {
    return iranProvinces.firstOrNull { it.id == provinceId }
}

/**
 * Returns full info for every province.
 */
fun getAllProvincesInfo(provinceId: String): List<ProvinceInfoModel> {
    return iranProvinces
}