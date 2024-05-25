package org.eu.sdsz.hanamaru.saucenao.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.eu.sdsz.hanamaru.saucenao.ui.component.ImagePicker

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    method: Boolean,
    onMethodChange: (Boolean)->Unit,
    onSelectImage: ()->Unit,
    imageUrl: String,
    onUrlChange: (String)->Unit,
    onSearch: ()->Unit,
    isSearching: Boolean
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ImagePicker(
                method = method,
                onMethodChange = onMethodChange,
                onSelectImage = onSelectImage,
                imageUrl = imageUrl,
                onUrlChange = onUrlChange,
                onSearch = onSearch,
                isSearching = isSearching
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(false, {}, {}, "", {}, {}, false)
}