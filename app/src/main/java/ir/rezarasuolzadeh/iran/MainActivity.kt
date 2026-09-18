package ir.rezarasuolzadeh.iran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ir.rezarasuolzadeh.iran.screen.CountyScreen
import ir.rezarasuolzadeh.iran.screen.ProvinceScreen
import ir.rezarasuolzadeh.iran.screen.IranScreen
import ir.rezarasuolzadeh.iran.ui.theme.IranTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )
        setContent {
            IranTheme {
                var selectedProvinceId by remember { mutableStateOf<String?>(value = null) }
                var selectedCountyId by remember { mutableStateOf<String?>(value = null) }
                when {
                    selectedProvinceId == null && selectedCountyId == null -> {
                        IranScreen(
                            onProvinceConfirmed = { id ->
                                selectedProvinceId = id
                            },
                            onBackPressed = {
                                selectedProvinceId = null
                                finish()
                            }
                        )
                    }

                    selectedProvinceId != null && selectedCountyId == null -> {
                        ProvinceScreen(
                            provinceId = selectedProvinceId.orEmpty(),
                            onCountyConfirmed = { id ->
                                selectedCountyId = id
                            },
                            onBackPressed = {
                                selectedProvinceId = null
                                selectedCountyId = null
                            }
                        )
                    }

                    selectedProvinceId != null && selectedCountyId != null -> {
                        CountyScreen(
                            provinceId = selectedProvinceId.orEmpty(),
                            countyId = selectedCountyId.orEmpty(),
                            onBackPressed = {
                                selectedProvinceId = null
                                selectedCountyId = null
                            }
                        )
                    }
                }
            }
        }
    }

}