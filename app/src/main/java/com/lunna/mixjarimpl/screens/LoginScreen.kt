package com.lunna.mixjarimpl.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lunna.mixjarimpl.utilities.DataStoreManager
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel


@Composable
fun LoginScreen() {
    val mixjarViewModel:MixjarViewModel by viewModel<MixjarViewModel>()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = DataStoreManager(context)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val userNameState = rememberSaveable { mutableStateOf("") }
        Surface(
            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
            modifier = Modifier.padding(8.dp)
        ) {
            TextField(
                value = userNameState.value,
                onValueChange = { userNameState.value = it },
                label = { Text("Username") },
                placeholder = { Text("Username") },
            )

        }


        Row(horizontalArrangement = Arrangement.End) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    println("Logged in!")
                    mixjarViewModel.userNameState.value = userNameState.value
                    scope.launch {
                        dataStore.saveToDataStore(username = userNameState.value)
                    }
                }
            ) {
                Text(
                    text = "Login",
                    color = MaterialTheme.colors.secondary
                )
            }
        }
        if (userNameState.value.isEmpty()) {
            Text(
                text = "Please enter mixcloud username!",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}