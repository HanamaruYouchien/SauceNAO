package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ResultList() {
    LazyColumn {
        items(20) {
            ResultItem()
        }
    }
}

@Preview
@Composable
fun PreviewResultList() {
    ResultList()
}