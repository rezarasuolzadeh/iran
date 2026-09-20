package ir.rezarasuolzadeh.iran.ui.components.ui

import androidx.compose.foundation.BorderStroke
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
 * A full-width button with an outline instead of a solid fill, used for secondary
 * actions (e.g. "cancel") that shouldn't compete with a [FillButton] on the same screen.
 *
 * @param text the label shown on the button.
 * @param enabled whether the button can be tapped; when `false` it's shown grayed out.
 * @param onClick called when the button is tapped.
 */
@Composable
fun BorderButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MediumBlue
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = White,
            contentColor = MediumBlue,
            disabledContainerColor = White,
            disabledContentColor = LightGray
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = Typography.bodyMedium.copy(color = MediumBlue)
        )
    }
}

@Preview
@Composable
fun BorderButtonPreview() {
    BorderButton(
        modifier = Modifier,
        text = "انصراف",
        onClick = {}
    )
}