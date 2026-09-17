package ir.rezarasuolzadeh.iran.components.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.ui.theme.Typography
import ir.rezarasuolzadeh.iran.ui.theme.White

@Composable
fun CountyBottomSheet(
    modifier: Modifier = Modifier,
    countyName: String?,
    isConfirmEnabled: Boolean,
    onConfirm: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
                text = stringResource(id = R.string.selected_county),
                style = Typography.bodySmall,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(height = 8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = countyName ?: stringResource(id = R.string.no_county_selected),
                style = Typography.bodyLarge,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(height = 20.dp))
            SelectButton(
                enabled = isConfirmEnabled,
                onClick = onConfirm
            )
        }
    }
}

@Preview
@Composable
fun CountyBottomSheetPreview() {
    CountyBottomSheet(
        modifier = Modifier,
        countyName = "اهواز",
        isConfirmEnabled = true,
        onConfirm = {}
    )
}