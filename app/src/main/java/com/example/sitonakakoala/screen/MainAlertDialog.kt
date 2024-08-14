package com.example.sitonakakoala.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sitonakakoala.ui.theme.SitonakaKoalaTheme

@Composable
fun MainAlertDialog(
    confirm: String,
    dismiss: String?,
    title: String?,
    text: String,
    onClick: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* no action */ },
        confirmButton = {
            TextButton(onClick = {
                onClick(true)
            }) {
                Text(text = confirm)
            }
        },
        dismissButton = {
            dismiss?.let {
                TextButton(onClick = {
                    onClick(false)
                }) {
                    Text(text = it)
                }
            }
        },
        title = {
            title?.let {
                Text(text = it)
            }
        },
        text = {
            Text(text = text)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainAlertDialogPreview() {
    SitonakaKoalaTheme {
        MainAlertDialog(
            confirm = "OK",
            dismiss = "CANCEL",
            title = "Title",
            text = "Text"
        ) {}
    }
}