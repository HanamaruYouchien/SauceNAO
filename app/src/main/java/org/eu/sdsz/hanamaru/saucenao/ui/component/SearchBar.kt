package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(onSearch: ()->Unit, isSearching: Boolean, enabled: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onSearch,
            modifier = Modifier.align(Alignment.CenterVertically),
            enabled = enabled
        ) {
            Text(text = "Search")
        }
        Row(modifier = Modifier.weight(1f)) {
            if (isSearching) {
                Spacer(modifier = Modifier.width(12.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(12.dp),
                    color = Color.Gray,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProgressBar() {
    SearchBar({}, true, true)
}