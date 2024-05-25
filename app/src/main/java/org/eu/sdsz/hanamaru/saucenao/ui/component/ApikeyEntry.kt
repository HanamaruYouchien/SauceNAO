package org.eu.sdsz.hanamaru.saucenao.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ApikeyEntry(curKey: String, onSave: (String)->Unit) {
    var apiKey by rememberSaveable {
        mutableStateOf(curKey)
    }
    Column{
        Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("Enter you API key")
                }
            }
        )
        }
        Column(horizontalAlignment = Alignment.End) {
            // value should be apiKey to trigger recomposition
            PasswordField(value = apiKey, onValueChange = { apiKey = it })
            Button(onClick = { onSave(apiKey) }) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun PasswordField(value: String, onValueChange: (String)->Unit) {
    var visible by rememberSaveable {
        mutableStateOf(false)
    }
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Change Visibility"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewApikeyEntry() {
    ApikeyEntry(curKey = "myKey") {}
}