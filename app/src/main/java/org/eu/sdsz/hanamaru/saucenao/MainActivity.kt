package org.eu.sdsz.hanamaru.saucenao

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.eu.sdsz.hanamaru.saucenao.ui.screen.AppScreen
import org.eu.sdsz.hanamaru.saucenao.ui.theme.SauceNAOTheme
import org.eu.sdsz.hanamaru.saucenao.viewmodel.PreferenceViewModel

class MainActivity : ComponentActivity() {
    lateinit var viewModel: PreferenceViewModel
    lateinit var getImageFileLauncher: ActivityResultLauncher<PickVisualMediaRequest> // should be placed in activity
    var imageFile = byteArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this).get(PreferenceViewModel::class.java)
        getImageFileLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                MainScope().launch(Dispatchers.IO) {
                    imageFile = getFileByUri(it)
                }
            }
        }

        setContent {
            SauceNAOTheme {
                AppScreen(
                    curApiKey = viewModel.apiKey,
                    onApiKeySave = { viewModel.apiKey = it },
                    onSelectImage = { getImageFileLauncher.launch(PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )) },
                    onSearch = {
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SauceNAOTheme {
        AppScreen("myKey", {}, {}, {})
    }
}