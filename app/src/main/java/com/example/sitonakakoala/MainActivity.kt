package com.example.sitonakakoala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.sitonakakoala.screen.HomeScreen
import com.example.sitonakakoala.screen.MainAlertDialog
import com.example.sitonakakoala.ui.theme.SitonakaKoalaTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {

    companion object {
        private lateinit var Instance: MainActivity
        suspend fun dialog(
            text: String,
            confirm: String = "OK",
            dismiss: String? = null,
            title: String? = null
        ): Boolean {
            return Instance.showAlertDialog(confirm, dismiss, title, text)
        }
    }

    private val confirmFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val dismissFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val titleFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val textFlow: MutableStateFlow<String> = MutableStateFlow("")

    private lateinit var alertContinuation: Continuation<Boolean>
    private suspend fun showAlertDialog(
        confirm: String,
        dismiss: String?,
        title: String?,
        text: String
    ): Boolean = suspendCoroutine {
        alertContinuation = it
        confirmFlow.value = confirm
        dismissFlow.value = dismiss
        titleFlow.value = title
        textFlow.value = text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Instance = this
        loggerTest()
        enableEdgeToEdge()
        setContent {
            SitonakaKoalaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                val confirm = confirmFlow.collectAsState()
                val dismiss = dismissFlow.collectAsState()
                val title = titleFlow.collectAsState()
                val text = textFlow.collectAsState()
                if (text.value.isNotEmpty()) {
                    MainAlertDialog(
                        confirm = confirm.value,
                        dismiss = dismiss.value,
                        title = title.value,
                        text = text.value
                    ) {
                        alertContinuation.resume(it)
                        textFlow.value = ""
                    }
                }
            }
        }
    }

    private fun loggerTest() {
        logger.trace("Logger TEST")
        logger.debug("Logger TEST")
        logger.info("Logger TEST")
        logger.warn("Logger TEST")
        logger.error("Logger TEST")
    }
}

val logger: Logger by lazy { LoggerFactory.getLogger("SitonakaKoala") }
