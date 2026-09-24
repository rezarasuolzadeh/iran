package ir.rezarasuolzadeh.iran.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.rezarasuolzadeh.iran.ui.components.map.CountyMap
import ir.rezarasuolzadeh.iran.ui.components.ui.BackButton
import ir.rezarasuolzadeh.iran.ui.components.ui.CountyBottomSheet
import ir.rezarasuolzadeh.iran.ui.components.ui.GithubButton
import ir.rezarasuolzadeh.iran.ui.components.ui.HeaderView
import ir.rezarasuolzadeh.iran.utils.extensions.openGithubRepository
import ir.rezarasuolzadeh.iran.ui.theme.LightBlue
import ir.rezarasuolzadeh.iran.utils.map.getCountyName
import ir.rezarasuolzadeh.iran.utils.map.getCountySeatName

/**
 * The final step of the flow: shows the single county the user picked on its own,
 * along with the name of its county seat.
 *
 * @param provinceId the province the county belongs to.
 * @param countyId the county being shown.
 * @param onBackPressed called when the user backs out of this screen.
 */
@Composable
internal fun CountyScreen(
    provinceId: String,
    countyId: String,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

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
            title = getCountyName(countyId = countyId).orEmpty(),
            description = "نقشه شهرستان انتخابی خود را مشاهده نمایید"
        )
        CountyMap(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(size = 200.dp),
            provinceId = provinceId,
            countyId = countyId
        )
        CountyBottomSheet(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            countySeatName = getCountySeatName(countyId = countyId).orEmpty(),
            onBack = onBackPressed
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun CountyScreenPreview() {
    CountyScreen(
        provinceId = "Qazvin",
        countyId = "Qazvin_1",
        onBackPressed = {}
    )
}