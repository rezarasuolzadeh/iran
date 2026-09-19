package ir.rezarasuolzadeh.iran.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ir.rezarasuolzadeh.iran.R

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(resId = R.font.vazir_bold)),
        fontWeight = FontWeight.Normal,
        color = Black,
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(resId = R.font.vazir)),
        fontWeight = FontWeight.Normal,
        color = MediumGray,
        textAlign = TextAlign.Center,
        fontSize = 12.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(resId = R.font.vazir_bold)),
        fontWeight = FontWeight.Normal,
        color = DarkGray,
        textAlign = TextAlign.Center,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(resId = R.font.vazir)),
        fontWeight = FontWeight.Normal,
        color = White,
        textAlign = TextAlign.Center,
        fontSize = 15.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(resId = R.font.vazir_bold)),
        fontWeight = FontWeight.Normal,
        color = Black,
        textAlign = TextAlign.Center,
        fontSize = 18.sp
    )
)