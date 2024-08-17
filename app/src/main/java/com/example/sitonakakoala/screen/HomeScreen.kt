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
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    var location by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier) {
        Text(text = "Location  $location")
        Button(onClick = {
            scope.launch {
                runCatching {
                    MainActivity.location()
                }.onSuccess {
                    location = it
                }.onFailure {
                    MainActivity.dialog(it.localizedMessage ?: "error")
                }
            }
        }) {
            Text(text = "Get Location")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SitonakaKoalaTheme {
        HomeScreen()
    }
}
