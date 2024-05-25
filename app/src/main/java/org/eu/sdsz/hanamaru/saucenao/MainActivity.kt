package org.eu.sdsz.hanamaru.saucenao

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.eu.sdsz.hanamaru.saucenao.data.AppState
import org.eu.sdsz.hanamaru.saucenao.data.SaucenaoResult
import org.eu.sdsz.hanamaru.saucenao.process.SauceNAO
import org.eu.sdsz.hanamaru.saucenao.ui.screen.AppScreen
import org.eu.sdsz.hanamaru.saucenao.ui.theme.SauceNAOTheme
import org.eu.sdsz.hanamaru.saucenao.viewmodel.PreferenceViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: PreferenceViewModel
    private lateinit var getImageFileLauncher: ActivityResultLauncher<PickVisualMediaRequest> // should be placed in activity
    private var imageFile = byteArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[PreferenceViewModel::class.java]
        getImageFileLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                MainScope().launch(Dispatchers.IO) {
                    imageFile = getFileByUri(it)
                }
            }
        }

        setContent {
            SauceNAOTheme {
                var imageUrl by rememberSaveable {
                    mutableStateOf("")
                }
                var method by rememberSaveable {
                    mutableStateOf(false)
                }
                var appState by rememberSaveable {
                    mutableStateOf(AppState.MAIN)
                }
                var resultData by rememberSaveable {
                    mutableStateOf<List<SaucenaoResult.Result>?>(null)
                }

                AppScreen(
                    appState = appState,
                    onAppStateChange = { appState = it },
                    curApiKey = viewModel.apiKey,
                    onApiKeySave = { viewModel.apiKey = it },
                    method = method,
                    onMethodChange = { method = it },
                    onSelectImage = { getImageFileLauncher.launch(PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )) },
                    imageUrl = imageUrl,
                    onUrlChange = { imageUrl = it },
                    resultData = resultData?: listOf(),
                    toUrl = { openUrl(it) },
                    onSearch = {
                        Log.d("onSearch", "method: $method")
                        MainScope().launch(Dispatchers.IO) {
                            val res = if (method) { SauceNAO.search(viewModel.apiKey, imageUrl) } else { SauceNAO.search(viewModel.apiKey, imageFile) }
                            Log.d("search", "$res")
                            MainScope().launch(Dispatchers.Main) {
                                when {
                                    null == res -> {
                                        Toast.makeText(this@MainActivity, "Request Failed. No Network Probably", Toast.LENGTH_LONG).show()
                                    }
                                    SauceNAO.STATUS_OK == res.header.status -> {
                                        resultData = res.results
                                        appState = AppState.RESULT
                                    }
                                    else -> {
                                        Toast.makeText(this@MainActivity, res.header.message?: "Unknown Error", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    private fun getFileByUri(uri: Uri): ByteArray {
        val stream = contentResolver.openInputStream(uri)
        return stream?.readBytes() ?: ByteArray(0)
    }

    private fun getImageByUri(uri: Uri): Bitmap {
        val source = ImageDecoder.createSource(contentResolver, uri)
        return ImageDecoder.decodeBitmap(source)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SauceNAOTheme {
        AppScreen(AppState.MAIN, {}, "myKey", {}, false, {}, {}, "", {}, listOf(), {}, {})
    }
}