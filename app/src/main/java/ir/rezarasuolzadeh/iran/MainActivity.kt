package ir.rezarasuolzadeh.iran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ir.rezarasuolzadeh.iran.jadid.IranMap
import ir.rezarasuolzadeh.iran.ui.theme.IranTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selectedProvince by remember { mutableStateOf<String?>(null) }
            IranTheme {
                IranMap(
                    modifier = Modifier,
                    selectedProvinceId = selectedProvince,
                    onProvinceSelected = { selectedProvinceId ->
                        selectedProvince = selectedProvinceId
                    }
                )
            }
        }
    }

}