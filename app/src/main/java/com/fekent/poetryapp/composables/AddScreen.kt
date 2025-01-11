package com.fekent.poetryapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fekent.poetryapp.ui.theme.PoetryAppTheme

@Composable
fun AddScreen(isAuthored: Boolean) {
    AddScreenUI(isAuthored = isAuthored)
}

@Composable
fun AddScreenUI(isAuthored: Boolean) {
    var poem by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }


    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(32.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Poem Title") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        BasicTextField(
            value = poem,
            onValueChange = { poem = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp) //padding separated so that the background color doesn't extend past the other TextFields
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        if (!isAuthored) {
            TextField(
                value = author,
                onValueChange = { author = it },
                label = { Text(text = "Author Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun AddScreenPreview() {
    PoetryAppTheme {
        AddScreen(isAuthored = false)
    }
}