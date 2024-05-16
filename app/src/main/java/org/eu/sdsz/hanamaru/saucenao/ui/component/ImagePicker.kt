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
fun ImagePicker() {
    var checked by rememberSaveable {
        mutableStateOf(false)
    }
    var url by rememberSaveable {
        mutableStateOf("")
    }

    Column {
        Row {
            Text(text = "Select a picture or input URL")
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }


        if (checked) {
            ImageUrlEntry(url = url) {
                url = it
            }
        } else {
            ImageFileSelector {}
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Search")
        }
    }
}

@Preview
@Composable
fun PreViewImagePiker() {
    ImagePicker()
}