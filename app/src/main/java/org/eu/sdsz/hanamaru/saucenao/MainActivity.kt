package org.eu.sdsz.hanamaru.saucenao

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import org.eu.sdsz.hanamaru.saucenao.ui.component.ApikeyEntry
import org.eu.sdsz.hanamaru.saucenao.ui.theme.SauceNAOTheme
import org.eu.sdsz.hanamaru.saucenao.viewmodel.PreferenceViewModel

class MainActivity : ComponentActivity() {
    lateinit var viewModel: PreferenceViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this).get(PreferenceViewModel::class.java)

        setContent {
            SauceNAOTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Preferences(curKey = viewModel.apiKey) {
                        viewModel.apiKey = it
                    }
                }
            }
        }
    }
}

@Composable
fun Preferences(curKey: String, onSave: (String)->Unit) {
    ApikeyEntry(curKey = curKey, onSave = onSave)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SauceNAOTheme {
        Preferences(curKey = "myKey") {}
    }
}