package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagePreviewer(imageUri: String) {
    AsyncImage(
        model = imageUri,
        contentDescription = "Preview",
        modifier = Modifier.width(200.dp).height(200.dp)
    )
}