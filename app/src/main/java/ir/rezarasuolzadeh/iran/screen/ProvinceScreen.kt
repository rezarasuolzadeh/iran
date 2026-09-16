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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.components.ProvincesMap
import ir.rezarasuolzadeh.iran.extensions.openGithubRepository
import ir.rezarasuolzadeh.iran.ui.theme.Black
import ir.rezarasuolzadeh.iran.ui.theme.DarkGray
import ir.rezarasuolzadeh.iran.ui.theme.LightBlue
import ir.rezarasuolzadeh.iran.ui.theme.LightGray
import ir.rezarasuolzadeh.iran.ui.theme.MediumBlue
import ir.rezarasuolzadeh.iran.ui.theme.MediumGray
import ir.rezarasuolzadeh.iran.ui.theme.White

@Composable
fun ProvinceScreen(
    onSelectedProvince: (id: String?) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    var selectedProvince by remember { mutableStateOf<String?>(value = null) }
    var selectedProvinceName by remember { mutableStateOf<String?>(value = null) }

    BackHandler {
        onBackPressed()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlue)
    ) {
        Card(
            modifier = Modifier
                .padding(top = 48.dp, start = 18.dp)
                .size(size = 40.dp)
                .align(alignment = Alignment.TopStart)
                .clickable(onClick = {}),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
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
                    contentDescription = "Back"
                )
            }
        }
        Card(
            modifier = Modifier
                .padding(top = 48.dp, end = 18.dp)
                .size(size = 40.dp)
                .align(alignment = Alignment.TopEnd)
                .clickable(onClick = {}),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            onClick = {
                context.openGithubRepository()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_github),
                    contentDescription = "Back"
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
                text = "انتخاب استان",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.vazir_bold)),
                    color = Black,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.height(height = 3.dp))
            Text(
                text = "لطفا استان مورد نظر خود را انتخاب نمایید",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.vazir)),
                    color = MediumGray,
                    fontSize = 12.sp
                )
            )
        }
        ProvincesMap(
            selectedProvinceId = selectedProvince,
            selectedProvinceName = { name ->
                selectedProvinceName = name
            },
            onProvinceSelected = { tapped ->
                selectedProvince = tapped
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
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "استان منتخب",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.vazir_bold)),
                        color = DarkGray,
                        fontSize = 12.sp
                    ),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(height = 8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = selectedProvinceName ?: "استانی انتخاب نشده است",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.vazir_bold)),
                        color = Black,
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
                        containerColor = MediumBlue,
                        contentColor = White,
                        disabledContainerColor = LightGray,
                        disabledContentColor = White
                    ),
                    onClick = {
                        onSelectedProvince(selectedProvince)
                    },
                    enabled = selectedProvince != null
                ) {
                    Text(
                        text = "تایید استان",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.vazir)),
                            color = White,
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
fun ProvinceScreenPreview() {
    ProvinceScreen(
        onSelectedProvince = {},
        onBackPressed = {}
    )
}