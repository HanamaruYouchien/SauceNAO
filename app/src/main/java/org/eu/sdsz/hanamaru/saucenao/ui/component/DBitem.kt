package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import org.eu.sdsz.hanamaru.saucenao.data.DBMask

@Composable
fun DBitem(db:DBMask.DB) {
    var ischecked = true
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = ischecked,
            onCheckedChange = { ischecked = it } )
        Text(text = db.siteName)
    }
}

@Composable
@Preview
fun PreviewDBitem() {
    DBitem(DBMask.DB.PIXIV)
}