package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImageFileSelector(onClick: ()->Unit) {
    Button(onClick = onClick) {
        Text(text = "Select Image")
    }
}

@Preview
@Composable
fun PreviewImageFileSelector() {
    ImageFileSelector {}
}