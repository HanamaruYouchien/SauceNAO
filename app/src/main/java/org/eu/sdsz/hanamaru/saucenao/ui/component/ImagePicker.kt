package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImagePicker(method: Boolean, onMethodChange: (Boolean)->Unit, onSelectImage: ()->Unit, imageUrl: String, onUrlChange: (String)->Unit, onSearch: ()->Unit, isSearching: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Select search method", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Local",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.width(6.dp))
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
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "URL",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Left
                )
            }
            if (method) {
                ImageUrlEntry(url = imageUrl, onValueChange = onUrlChange)
            } else {
                ImageFileSelector(onClick = onSelectImage)
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSearching) {
                Spacer(modifier = Modifier.width(24.dp))
            }
            Button(
                onClick = onSearch,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = "Search")
            }

            if (isSearching) {
                Spacer(modifier = Modifier.width(12.dp))
                ProgressBar()
            }
        }
    }
}

@Preview
@Composable
fun PreViewImagePiker() {
    ImagePicker(false, {}, {}, "https://example.com", {}, {}, true)
}