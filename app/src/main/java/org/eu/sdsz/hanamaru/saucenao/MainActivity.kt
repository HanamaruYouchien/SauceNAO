package org.eu.sdsz.hanamaru.saucenao

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import org.eu.sdsz.hanamaru.saucenao.ui.component.ApikeyEntry
import org.eu.sdsz.hanamaru.saucenao.ui.screen.AppScreen
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
                AppScreen(curApiKey = viewModel.apiKey) {
                    viewModel.apiKey = it
                }
            }
        }

        val getImageFileLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {}
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SauceNAOTheme {
        AppScreen("myKey") {}
    }
}