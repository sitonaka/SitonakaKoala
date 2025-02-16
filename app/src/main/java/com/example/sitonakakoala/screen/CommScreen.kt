package com.example.sitonakakoala.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sitonakakoala.MainActivity
import com.example.sitonakakoala.database.MyDatabase
import com.example.sitonakakoala.server.GHUsers
import com.example.sitonakakoala.ui.theme.SitonakaKoalaTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun CommScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val dao = MyDatabase.getDatabase(LocalContext.current).ghDao()
    val users = dao.getAllUsersFlow().collectAsState(initial = listOf())
    var login by rememberSaveable { mutableStateOf("sitonaka") }
    Column(modifier = modifier) {
        Text(text = "Login:  $login")
        Button(onClick = {
            scope.launch {
                runCatching {
                    HttpClient(CIO) {
                        install(ContentNegotiation) {
                            json(
                                Json {
                                    prettyPrint = true
                                    isLenient = true
                                    ignoreUnknownKeys = true
                                }
                            )
                        }
                        install(Logging)
                    }.use { client ->
                        val ghUsers: GHUsers = client.get("https://api.github.com/users/$login").body()
                        dao.insertUsers(ghUsers)
                    }
                }.onFailure {
                    MainActivity.dialog(it.localizedMessage ?: "error")
                }
            }
        }) {
            Text(text = "USERS API")
        }
        HorizontalDivider()
        UsersItems(list = users.value)
    }
}

@Composable
fun UsersItems(list: List<GHUsers>) {
    LazyColumn {
        items(list) {
            UsersItem(ghUsers = it)
            HorizontalDivider()
        }
    }
}

@Composable
fun UsersItem(ghUsers: GHUsers) {
    Column {
        Text(text = ghUsers.login, style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "updateAt", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = ghUsers.updatedAt, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommScreenPreview() {
    SitonakaKoalaTheme {
        CommScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun UsersItemsPreview() {
    SitonakaKoalaTheme {
        UsersItems(
            listOf(
                GHUsers(
                    id = 0,
                    login = "login name",
                    publicRepos = 99,
                    reposUrl = "http://google.com",
                    updatedAt = "2000/12/31"
                ),
                GHUsers(
                    id = 1,
                    login = "login name 2",
                    publicRepos = 999,
                    reposUrl = "https://google.com",
                    updatedAt = "2001/12/31"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsersItemPreview() {
    SitonakaKoalaTheme {
        UsersItem(
            GHUsers(
                id = 0,
                login = "login name",
                publicRepos = 99,
                reposUrl = "http://google.com",
                updatedAt = "2000/12/31"
            )
        )
    }
}
