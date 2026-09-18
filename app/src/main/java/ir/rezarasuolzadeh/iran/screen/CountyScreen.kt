package ir.rezarasuolzadeh.iran.screen

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
import ir.rezarasuolzadeh.iran.components.map.CountyMap
import ir.rezarasuolzadeh.iran.components.ui.BackButton
import ir.rezarasuolzadeh.iran.components.ui.CountyBottomSheet
import ir.rezarasuolzadeh.iran.components.ui.GithubButton
import ir.rezarasuolzadeh.iran.components.ui.HeaderView
import ir.rezarasuolzadeh.iran.extensions.openGithubRepository
import ir.rezarasuolzadeh.iran.ui.theme.LightBlue
import ir.rezarasuolzadeh.iran.utils.getCountyName
import ir.rezarasuolzadeh.iran.utils.getCountySeatName

@Composable
fun CountyScreen(
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
            centerName = getCountySeatName(countyId = countyId).orEmpty(),
            onBack = onBackPressed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountyScreenPreview() {
    CountyScreen(
        provinceId = "Qazvin",
        countyId = "Qazvin_1",
        onBackPressed = {}
    )
}