package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.items
import org.eu.sdsz.hanamaru.saucenao.data.SaucenaoResult

@Composable
fun ResultList(data: List<SaucenaoResult.Result>) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        items(data) {
            val urls by rememberSaveable {
                mutableStateOf(it.getUrls())
            }
            ResultItem(
                thumbnail = it.header.thumbnail,
                title = it.getTitle(),
                author = it.getAuthor(),
                link = if (urls.isNotEmpty()) {urls[0]} else {""},
                similarity = it.header.similarity
            )
        }
    }
}

@Preview
@Composable
fun PreviewResultList() {
    ResultList(listOf())
}