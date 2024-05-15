package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImageUrlEntry(url: String, onValueChange: (String)->Unit) {
    TextField(value = url, onValueChange = onValueChange)
}

@Preview
@Composable
fun PreviewImageUrlEntry() {
    ImageUrlEntry("") {}
}