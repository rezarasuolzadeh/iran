package ir.rezarasuolzadeh.iran.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.components.map.ProvinceMap
import ir.rezarasuolzadeh.iran.components.ui.BackButton
import ir.rezarasuolzadeh.iran.components.ui.ProvinceBottomSheet
import ir.rezarasuolzadeh.iran.components.ui.GithubButton
import ir.rezarasuolzadeh.iran.components.ui.HeaderView
import ir.rezarasuolzadeh.iran.extensions.calculateSizeAccordingToProvince
import ir.rezarasuolzadeh.iran.extensions.openGithubRepository
import ir.rezarasuolzadeh.iran.ui.theme.LightBlue

@Composable
fun ProvinceScreen(
    provinceId: String,
    onSelectedCounty: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val selectedCountyByProvince = remember { mutableStateMapOf<String, String?>() }
    val selectedCountyId = selectedCountyByProvince[provinceId]
    var selectedCountyName by remember { mutableStateOf<String?>(value = null) }

    BackHandler {
        onBackPressed()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlue)
    ) {
        BackButton(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            onClick = onBackPressed
        )
        GithubButton(
            modifier = Modifier.align(alignment = Alignment.TopEnd),
            onClick = {
                context.openGithubRepository()
            }
        )
        HeaderView(
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            title = stringResource(id = R.string.select_county),
            description = stringResource(id = R.string.select_your_county_please)
        )
        ProvinceMap(
            modifier = Modifier
                .calculateSizeAccordingToProvince(provinceId = provinceId)
                .align(alignment = Alignment.Center),
            provinceId = provinceId,
            selectedCityId = selectedCountyId,
            selectedCityName = { name ->
                selectedCountyName = name
            },
            onCitySelected = { tapped ->
                selectedCountyByProvince[provinceId] = tapped
            }
        )
        ProvinceBottomSheet(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            countyName = selectedCountyName,
            isConfirmEnabled = selectedCountyId != null,
            onConfirm = {
                onSelectedCounty(selectedCountyId.orEmpty())
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProvinceScreenPreview() {
    ProvinceScreen(
        provinceId = "Qazvin",
        onSelectedCounty = {},
        onBackPressed = {}
    )
}