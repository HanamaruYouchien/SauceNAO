package org.eu.sdsz.hanamaru.saucenao.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.eu.sdsz.hanamaru.saucenao.data.AppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(curApiKey: String, onApiKeySave: (String)->Unit) {
    var state by rememberSaveable {
        mutableStateOf(AppState.MAIN)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "SauceNAO") },
                navigationIcon = {
                    if (AppState.MAIN != state) {
                        IconButton(onClick = { state = AppState.MAIN }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (AppState.MAIN == state) {
                        IconButton(onClick = { state = AppState.PREFERENCE }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "Preferences")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when(state) {
                AppState.MAIN -> { MainScreen() }
                AppState.PREFERENCE -> { PreferenceScreen(curApiKey = curApiKey, onSave = onApiKeySave) }
                AppState.RESULT -> { ResultScreen() }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAppScreen() {
    AppScreen("myKey") {}
}