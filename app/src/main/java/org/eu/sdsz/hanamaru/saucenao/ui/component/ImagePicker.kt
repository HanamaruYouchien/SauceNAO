package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text

@Composable
fun ImagePicker(method: Boolean, onMethodChange: (Boolean)->Unit, onSelectImage: ()->Unit, imageUrl: String, onUrlChange: (String)->Unit, onSearch: ()->Unit) {
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