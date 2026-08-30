package ir.rezarasuolzadeh.iran.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.map.county.ProvinceCityMap
import ir.rezarasuolzadeh.iran.map.province.IranMap

@Composable
fun CountyScreen(
    provinceId: String,
    onSelectedCounty: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val selectedCityByProvince = remember { mutableStateMapOf<String, String?>() }
    val selectedCityId = selectedCityByProvince[provinceId]
    var selectedCityName by remember { mutableStateOf<String?>(value = null) }

    BackHandler {
        onBackPressed()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6EFFC))
    ) {
        Card(
            modifier = Modifier
                .padding(top = 48.dp, start = 18.dp)
                .size(size = 40.dp)
                .align(alignment = Alignment.TopStart),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            onClick = {
                onBackPressed()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color(0xFF333333)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 96.dp)
                .align(alignment = Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "انتخاب شهرستان",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.vazir_bold)),
                    color = Color.Black,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.height(height = 3.dp))
            Text(
                text = "لطفا شهرستان مورد نظر خود را انتخاب نمایید",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.vazir)),
                    color = Color(0xFF5D5D5D),
                    fontSize = 12.sp
                )
            )
        }
        ProvinceCityMap(
            provinceId = provinceId,
            selectedCityId = selectedCityId,
            selectedCityName = { name ->
                selectedCityName = name
            },
            onCitySelected = { tapped ->
                selectedCityByProvince[provinceId] = tapped
            }
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(alignment = Alignment.BottomCenter),
            shape = RoundedCornerShape(size = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "شهرستان منتخب",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.vazir_bold)),
                        color = Color(0xFF575757),
                        fontSize = 12.sp
                    ),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(height = 8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = selectedCityName ?: "شهرستانی انتخاب نشده است",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.vazir_bold)),
                        color = Color.Black,
                        fontSize = 18.sp
                    ),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(height = 20.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(size = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1578D9),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFB0BEC5),
                        disabledContentColor = Color.White
                    ),
                    onClick = {
                        onSelectedCounty(selectedCityName.orEmpty())
                    },
                    enabled = selectedCityId != null
                ) {
                    Text(
                        text = "تایید شهرستان",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.vazir)),
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountyScreenPreview() {
    CountyScreen(
        provinceId = "Qazvin",
        onSelectedCounty = {},
        onBackPressed = {}
    )
}