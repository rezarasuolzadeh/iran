package ir.rezarasuolzadeh.iran.ui.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.rezarasuolzadeh.iran.ui.theme.LightGray
import ir.rezarasuolzadeh.iran.ui.theme.MediumBlue
import ir.rezarasuolzadeh.iran.ui.theme.Typography
import ir.rezarasuolzadeh.iran.ui.theme.White

/**
 * A full-width button with a solid fill, used for the primary action on a screen
 * (e.g. "confirm"). For a secondary action, see [BorderButton].
 *
 * @param text the label shown on the button.
 * @param enabled whether the button can be tapped; when `false` it's shown grayed out.
 * @param onClick called when the button is tapped.
 */
@Composable
fun FillButton(
    modifier: Modifier = Modifier,
    text: String,
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
            text = text,
            style = Typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun FillButtonPreview() {
    FillButton(
        modifier = Modifier,
        text = "تایید",
        onClick = {}
    )
}