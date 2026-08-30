package ir.rezarasuolzadeh.iran.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import ir.rezarasuolzadeh.iran.map.county.ProvinceCityMap

@Composable
fun CountyScreen(provinceId: String) {
    val selectedCityByProvince = remember { mutableStateMapOf<String, String?>() }
    val selectedCityId = selectedCityByProvince[provinceId]
    ProvinceCityMap(
        provinceId = provinceId,
        selectedCityId = selectedCityId,
        onCitySelected = { tapped ->
            selectedCityByProvince[provinceId] = tapped
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CountyScreenPreview() {
    CountyScreen(provinceId = "Qazvin")
}