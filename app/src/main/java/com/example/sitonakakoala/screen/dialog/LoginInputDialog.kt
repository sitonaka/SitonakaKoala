package com.example.sitonakakoala.screen.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.sitonakakoala.ui.theme.SitonakaKoalaTheme

@Composable
fun LoginInputDialog(onDismiss: () -> Unit, onDone: (String) -> Unit) {
    var login by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    Dialog(onDismissRequest = { /* no action */ }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = login,
                    onValueChange = {
                        errorMessage = when {
                            it.contains("\n") -> "contains RETURN"
                            it.length > LOGIN_MAX_LENGTH -> "length ${it.length} ($LOGIN_MAX_LENGTH)"
                            else -> ""
                        }
                        login = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    label = {
                        Text(text = "login")
                    },
                    placeholder = {
                        Text(text = "login name")
                    },
                    isError = errorMessage.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onDone(login) }
                    ),
                    singleLine = true,
                    maxLines = 1
                )
                SideEffect {
                    focusRequester.requestFocus()
                }
                Text(text = errorMessage)
                Row {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                    TextButton(
                        onClick = { onDone(login) },
                        enabled = login.isNotEmpty() && errorMessage.isEmpty()
                    ) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
}

const val LOGIN_MAX_LENGTH = 32

@Preview(showBackground = true)
@Composable
fun LoginInputDialogPreview() {
    SitonakaKoalaTheme {
        LoginInputDialog({}, {})
    }
}
