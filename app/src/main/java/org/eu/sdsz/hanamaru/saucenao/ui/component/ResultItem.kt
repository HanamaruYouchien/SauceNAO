package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResultItem() {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)) {
        Row (modifier = Modifier.padding(6.dp)) {
            Image(imageVector = Icons.Default.Image, contentDescription = "thumbnail")
            // some column might not exist, should be handled by parser
            Column {
                Text(text = "Title")
                Text(text = "author")
                Text(text = "link")
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "similarity")
        }
    }
}

@Preview
@Composable
fun PreviewResultItem() {
    ResultItem()
}