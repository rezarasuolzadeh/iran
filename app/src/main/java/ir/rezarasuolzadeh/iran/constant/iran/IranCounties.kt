package ir.rezarasuolzadeh.iran.constant.iran

import ir.rezarasuolzadeh.iran.constant.county.*
import ir.rezarasuolzadeh.iran.model.info.CountyInfoModel

/**
 * Every county in Iran, assembled by concatenating each province's own county list.
 * This is the single source `getCounties`/`getCountyInfo` filter against by `provinceId`.
 */
val iranCounties: List<CountyInfoModel> = (
    azerbaijaneGharbiCounties +
    azerbaijaneSharghiCounties +
    ardabilCounties +
    gilanCounties +
    kurdistanCounties +
    kermanshahCounties +
    ilamCounties +
    khuzestanCounties +
    bushehrCounties +
    hormozganCounties +
    sistanVaBaluchestanCounties +
    khorasaneJonubiCounties +
    semnanCounties +
    khorasaneRazaviCounties +
    khorasaneShomaliCounties +
    golestanCounties +
    mazandaranCounties +
    tehranCounties +
    alborzCounties +
    qazvinCounties +
    qomCounties +
    lorestanCounties +
    isfahanCounties +
    hamedanCounties +
    zanjanCounties +
    markaziCounties +
    kermanCounties +
    farsCounties +
    kohgiluyehVaBoyerahmadCounties +
    yazdCounties +
    chaharmahalVaBakhtiariCounties
)