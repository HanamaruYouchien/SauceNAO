package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ResultItem(thumbnail: String, title: String, author: String, link: String, similarity: Float, toUrl: (String) -> Unit) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)) {
        Row (modifier = Modifier.padding(6.dp)) {
            AsyncImage(
                model = thumbnail,
                contentDescription = "thumbnail",
                modifier = Modifier.width(100.dp),
                contentScale = ContentScale.Crop
            )
            // some column might not exist, should be handled by parser
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title)
                Text(text = author)
//                Text(text = link)
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF00BFFF), textDecoration = TextDecoration.Underline)){
                            append(link)
                        }
                    }
                ) {
                    toUrl(link)
                }
            }
            Text(text = "${similarity}%")
        }
    }
}

@Preview
@Composable
fun PreviewResultItem() {
    ResultItem("", "", "", "", 0f, {})
}