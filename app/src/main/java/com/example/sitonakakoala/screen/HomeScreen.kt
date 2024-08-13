package com.example.sitonakakoala.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sitonakakoala.ui.theme.SitonakaKoalaTheme


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hello HomeScreen!")
        Button(onClick = {
            // Under construction
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
