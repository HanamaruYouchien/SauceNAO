package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier.size(12.dp),
        color = Color.Gray,
        strokeWidth = 2.dp)
}

@Preview
@Composable
fun PreviewProgressBar() {
    ProgressBar()
}