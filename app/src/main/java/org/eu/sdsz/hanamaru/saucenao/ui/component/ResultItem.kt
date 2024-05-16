package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ResultItem() {
    Card {
        Row {
            Image(imageVector = Icons.Default.Image, contentDescription = "thumbnail")
            // some column might not exist, should be handled by parser
            Column {
                Text(text = "Title")
                Text(text = "author")
                Text(text = "link")
            }
            Text(text = "similarity")
        }
    }
}

@Preview
@Composable
fun PreviewResultItem() {
    ResultItem()
}