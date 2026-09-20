package ir.rezarasuolzadeh.iran.ui.components.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.rezarasuolzadeh.iran.ui.theme.Typography

/**
 * A simple centered title and description, used at the top of a screen to
 * explain what the user should do there.
 *
 * @param title the main heading text.
 * @param description a short line of supporting text shown under the title.
 */
@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier.padding(top = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = Typography.titleLarge
        )
        Spacer(modifier = Modifier.height(height = 3.dp))
        Text(
            text = description,
            style = Typography.titleSmall
        )
    }
}

@Preview
@Composable
fun HeaderViewPreview() {
    HeaderView(
        modifier = Modifier,
        title = "عنوان",
        description = "توضیحات مورد نیاز برای عنوان"
    )
}