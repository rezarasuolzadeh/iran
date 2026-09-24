package ir.rezarasuolzadeh.iran.ui.screen

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
import ir.rezarasuolzadeh.iran.ui.components.map.ProvinceMap
import ir.rezarasuolzadeh.iran.ui.components.ui.BackButton
import ir.rezarasuolzadeh.iran.ui.components.ui.ProvinceBottomSheet
import ir.rezarasuolzadeh.iran.ui.components.ui.GithubButton
import ir.rezarasuolzadeh.iran.ui.components.ui.HeaderView
import ir.rezarasuolzadeh.iran.utils.extensions.calculateSizeAccordingToProvince
import ir.rezarasuolzadeh.iran.utils.extensions.openGithubRepository
import ir.rezarasuolzadeh.iran.ui.theme.LightBlue

/**
 * The second step of the flow: shows a zoomed-in map of one province so the user can
 * pick a county within it. Each province remembers its own selected county, so
 * navigating back and forth between provinces doesn't lose the user's picks.
 *
 * @param provinceId the province whose counties are being shown.
 * @param onCountyConfirmed called with the chosen county's id once the user confirms it.
 * @param onBackPressed called when the user backs out of this screen.
 */
@Composable
internal fun ProvinceScreen(
    provinceId: String,
    onCountyConfirmed: (countyId: String) -> Unit,
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
        GithubButton(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            onClick = {
                context.openGithubRepository()
            }
        )
        BackButton(
            modifier = Modifier.align(alignment = Alignment.TopEnd),
            onClick = onBackPressed
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
            selectedCountyId = selectedCountyId,
            onCountyNameSelected = { name ->
                selectedCountyName = name
            },
            onCountyIdSelected = { id ->
                selectedCountyByProvince[provinceId] = id
            }
        )
        ProvinceBottomSheet(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            countyName = selectedCountyName,
            isConfirmEnabled = selectedCountyId != null,
            onConfirm = {
                onCountyConfirmed(selectedCountyId.orEmpty())
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun ProvinceScreenPreview() {
    ProvinceScreen(
        provinceId = "Qazvin",
        onCountyConfirmed = {},
        onBackPressed = {}
    )
}