package ir.rezarasuolzadeh.iran.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import ir.rezarasuolzadeh.iran.map.province.IranMap

@Composable
fun ProvinceScreen(onSelectedProvince: (id: String) -> Unit) {
    var selectedProvince by remember { mutableStateOf<String?>(value = null) }
    IranMap(
        selectedProvinceId = selectedProvince,
        onProvinceSelected = { tapped ->
            selectedProvince = tapped
            onSelectedProvince(tapped.orEmpty())
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProvinceScreenPreview() {
    ProvinceScreen(
        onSelectedProvince = {}
    )
}