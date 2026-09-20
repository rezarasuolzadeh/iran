@file:JvmName("CountyBottomSheetKt")

package ir.rezarasuolzadeh.iran.ui.components.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.ui.theme.Typography
import ir.rezarasuolzadeh.iran.ui.theme.White

/**
 * A sheet anchored to the bottom of the screen that shows the county seat's name for the
 * county the user landed on, with a button to go back.
 *
 * @param countySeatName the name of the county's seat (its administrative center city).
 * @param onBack called when the back button is tapped.
 */
@Composable
fun CountyBottomSheet(
    modifier: Modifier = Modifier,
    countySeatName: String,
    onBack: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.center_of_selected_county),
                style = Typography.bodySmall,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(height = 8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = countySeatName,
                style = Typography.bodyLarge,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(height = 20.dp))
            FillButton(
                text = stringResource(id = R.string.back),
                onClick = onBack
            )
        }
    }
}

@Preview
@Composable
fun CountyBottomSheetPreview() = CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
    CountyBottomSheet(
        modifier = Modifier,
        countySeatName = "برازجان",
        onBack = {}
    )
}