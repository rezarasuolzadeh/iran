package ir.rezarasuolzadeh.iran.ui.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.rezarasuolzadeh.iran.R
import ir.rezarasuolzadeh.iran.ui.theme.Typography
import ir.rezarasuolzadeh.iran.ui.theme.White

@Composable
fun ExitDialog(
    onConfirmClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = onCancelClicked
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(size = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column(
                modifier = Modifier.padding(all = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.exit),
                    style = Typography.titleLarge
                )
                Spacer(modifier = Modifier.height(height = 12.dp))
                Text(
                    text = stringResource(id = R.string.are_you_want_to_exit),
                    style = Typography.bodySmall
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FillButton(
                        modifier = Modifier.weight(weight = 1f),
                        text = stringResource(id = R.string.yes),
                        onClick = onConfirmClicked
                    )
                    BorderButton(
                        modifier = Modifier.weight(weight = 1f),
                        text = stringResource(id = R.string.no),
                        onClick = onCancelClicked
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ExitDialogPreview() {
    ExitDialog(
        onConfirmClicked = {},
        onCancelClicked = {}
    )
}