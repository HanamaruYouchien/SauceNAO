package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import org.eu.sdsz.hanamaru.saucenao.data.DBMask

@Composable
fun SelectDB() {
    Column{
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("Select Target Databases")
                }
            }
        )
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .verticalScroll(rememberScrollState())
        ) {
            for (item in DBMask.DB.entries) {
                DBitem(item)
            }
        }
    }
}

@Composable
@Preview
fun PreviewSelectDB() {
    SelectDB()
}