package ir.rezarasuolzadeh.iran.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.ui.theme.LightGray
import ir.rezarasuolzadeh.iran.ui.theme.MediumBlue
import ir.rezarasuolzadeh.iran.ui.theme.Typography
import ir.rezarasuolzadeh.iran.ui.theme.White

@Composable
fun SelectButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(size = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MediumBlue,
            contentColor = White,
            disabledContainerColor = LightGray,
            disabledContentColor = White
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = stringResource(id = R.string.confirm_county),
            style = Typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun SelectButtonPreview() {
    SelectButton(
        modifier = Modifier,
        onClick = {}
    )
}