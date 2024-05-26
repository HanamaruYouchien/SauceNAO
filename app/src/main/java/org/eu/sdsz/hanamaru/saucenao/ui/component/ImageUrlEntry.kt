package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImageUrlEntry(url: String, onValueChange: (String)->Unit) {
    TextField(
        value = url,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(onClick = { onValueChange("") } )  {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear input URL"
                )
            }
        })
}

@Preview
@Composable
fun PreviewImageUrlEntry() {
    ImageUrlEntry("https://news.cnr.cn/mryxh/images/banner.jpg") {}
}