package ir.rezarasuolzadeh.iran.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.components.map.IranMap
import ir.rezarasuolzadeh.iran.components.ui.BackButton
import ir.rezarasuolzadeh.iran.components.ui.GithubButton
import ir.rezarasuolzadeh.iran.components.ui.HeaderView
import ir.rezarasuolzadeh.iran.components.ui.IranBottomSheet
import ir.rezarasuolzadeh.iran.extensions.openGithubRepository
import ir.rezarasuolzadeh.iran.ui.theme.LightBlue

@Composable
fun IranScreen(
    onProvinceConfirmed: (provinceId: String) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    var selectedProvinceId by remember { mutableStateOf<String?>(value = null) }
    var selectedProvinceName by remember { mutableStateOf<String?>(value = null) }

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
            title = stringResource(id = R.string.select_province),
            description = stringResource(id = R.string.select_your_province_please)
        )
        IranMap(
            selectedProvinceId = selectedProvinceId,
            onProvinceNameSelected = { name ->
                selectedProvinceName = name
            },
            onProvinceIdSelected = { id ->
                selectedProvinceId = id
            }
        )
        IranBottomSheet(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            provinceName = selectedProvinceName,
            isConfirmEnabled = selectedProvinceId != null,
            onConfirm = {
                onProvinceConfirmed(selectedProvinceId.orEmpty())
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IranScreenPreview() {
    IranScreen(
        onProvinceConfirmed = {},
        onBackPressed = {}
    )
}