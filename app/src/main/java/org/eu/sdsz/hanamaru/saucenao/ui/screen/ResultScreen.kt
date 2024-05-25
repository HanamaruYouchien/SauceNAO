package org.eu.sdsz.hanamaru.saucenao.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.eu.sdsz.hanamaru.saucenao.data.SaucenaoResult
import org.eu.sdsz.hanamaru.saucenao.ui.component.ResultList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResultScreen(resultData: List<SaucenaoResult.Result>, toUrl : (String) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            ResultList(resultData, toUrl = toUrl)
        }
    }
}

@Preview
@Composable
fun PreviewResultScreen() {
    ResultScreen(listOf(), {})
}