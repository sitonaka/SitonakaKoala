package com.example.sitonakakoala.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sitonakakoala.logger
import com.example.sitonakakoala.ui.theme.SitonakaKoalaTheme
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(modifier: Modifier = Modifier, onFinish: () -> Unit) {
    LaunchedEffect(key1 = true) {
        logger.trace("SplashScreen LaunchedEffect START")
        delay(3_000)
        onFinish()
        logger.trace("SplashScreen LaunchedEffect END")
    }
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "loading")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SitonakaKoalaTheme {
        SplashScreen {}
    }
}
