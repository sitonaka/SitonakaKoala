package com.example.sitonakakoala.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sitonakakoala.MainActivity
import com.example.sitonakakoala.ui.theme.SitonakaKoalaTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch

@Composable
fun CommScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var login by rememberSaveable { mutableStateOf("sitonaka") }
    var responseBody by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier) {
        Text(text = "Login:  $login")
        Button(onClick = {
            scope.launch {
                runCatching {
                    val client = HttpClient(CIO)
                    val response = client.get("https://api.github.com/users/$login")
                    response.bodyAsText()
                }.onSuccess {
                    responseBody = it
                }.onFailure {
                    MainActivity.dialog(it.localizedMessage ?: "error")
                }
            }
        }) {
            Text(text = "USERS API")
        }
        Text(text = responseBody)
    }
}

@Preview(showBackground = true)
@Composable
fun CommScreenPreview() {
    SitonakaKoalaTheme {
        CommScreen()
    }
}
