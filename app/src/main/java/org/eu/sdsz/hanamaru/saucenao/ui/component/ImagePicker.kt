package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ImagePicker(method: Boolean, onMethodChange: (Boolean)->Unit, onSelectImage: ()->Unit, imageUrl: String, onUrlChange: (String)->Unit, onSearch: ()->Unit) {
    Column {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Select search method")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.widthIn(64.dp,256.dp)
            ) {
                Text(text = "Local image"
                ,modifier = Modifier.weight(1f))
                Switch(
                    checked = method,
                    onCheckedChange = onMethodChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.secondary,
                        checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text("URL",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Right)
            }
        }


        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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
}

@Preview
@Composable
fun PreViewImagePiker() {
    ImagePicker(false, {}, {}, "https://example.com", {}, {})
}