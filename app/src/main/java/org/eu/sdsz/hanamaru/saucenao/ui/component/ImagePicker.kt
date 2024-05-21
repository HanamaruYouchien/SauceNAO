package org.eu.sdsz.hanamaru.saucenao.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.eu.sdsz.hanamaru.saucenao.process.search

@Composable
fun ImagePicker(method: Boolean, onMethodChange: (Boolean)->Unit, onSelectImage: ()->Unit, imageUrl: String, onUrlChange: (String)->Unit, onSearch: ()->Unit) {

    val exampleBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    println("return value:${search(exampleBitmap)}")


    Column {
        Row {
            Text(text = "Select a picture or input URL")
            Switch(
                checked = method,
                onCheckedChange = onMethodChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }


        if (method) {
            ImageUrlEntry(url = imageUrl, onValueChange = onUrlChange)
        } else {
            ImageFileSelector(onClick = onSelectImage)
        }

        Button(onClick = onSearch) {
            Text(text = "Search")
        }
    }
}

@Preview
@Composable
fun PreViewImagePiker() {
    ImagePicker(false, {}, {}, "https://example.com", {}, {})
}