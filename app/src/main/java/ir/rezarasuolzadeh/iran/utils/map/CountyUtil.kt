package ir.rezarasuolzadeh.iran.utils.map

import ir.rezarasuolzadeh.iran.constant.iran.iranCounties
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel

/**
 * Returns all counties that belong to the given province.
 *
 * @param provinceId the unique identifier of the parent province.
 */
fun getCounties(provinceId: String): List<CountyInfoModel> {
    return iranCounties.filter { it.provinceId == provinceId }
}

/**
 * Returns number of all counties that belong to the given province.
 *
 * @param provinceId the unique identifier of the parent province.
 */
fun getNumberOfCounties(provinceId: String): Int {
    return iranCounties.filter { it.provinceId == provinceId }.size
}

/**
 * Returns the display name of a county, or `null` if [countyId] doesn't match any county.
 *
 * @param countyId the unique identifier of the county.
 */
fun getCountyName(countyId: String): String? {
    return iranCounties.firstOrNull { it.id == countyId }?.name
}

/**
 * Returns the name of a county's seat (its administrative center city),
 * or `null` if [countyId] doesn't match any county.
 *
 * @param countyId the unique identifier of the county.
 */
fun getCountySeatName(countyId: String): String? {
    return iranCounties.firstOrNull { it.id == countyId }?.countySeatName
}

/**
 * Returns full info for a county — including its map path and display name —
 * or `null` if [countyId] doesn't match any county.
 *
 * @param countyId the unique identifier of the county.
 */
fun getCountyInfo(countyId: String): CountyInfoModel? {
    return iranCounties.firstOrNull { it.id == countyId }
}

/**
 * Returns full info for every county, across all provinces.
 */
fun getAllCountiesInfo(countyId: String): List<CountyInfoModel> {
    return iranCounties
}