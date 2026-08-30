package ir.rezarasuolzadeh.iran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ir.rezarasuolzadeh.iran.screen.CountyScreen
import ir.rezarasuolzadeh.iran.screen.ProvinceScreen
import ir.rezarasuolzadeh.iran.ui.theme.IranTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IranTheme {
                var selectedProvinceId by remember { mutableStateOf<String?>(value = null) }
                if (selectedProvinceId == null) {
                    ProvinceScreen(
                        onSelectedProvince = { id ->
                            selectedProvinceId = id
                        }
                    )
                } else {
                    CountyScreen(provinceId = selectedProvinceId.orEmpty())
                }
            }
        }
    }

}