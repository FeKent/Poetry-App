package com.fekent.poetryapp.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fekent.poetryapp.ui.theme.PoetryAppTheme

@Composable
fun AddScreen() {
    AddScreenUI(isAuthored = true)
}

@Composable
fun AddScreenUI(isAuthored: Boolean) {
    var poem by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }


    Column(Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(32.dp))
        TextField(value = title, onValueChange = {title = it} )
        Spacer(modifier = Modifier.size(16.dp))
        BasicTextField(value = poem, onValueChange = {poem = it})
        Spacer(modifier = Modifier.size(16.dp))
        if (!isAuthored){
            TextField(value = author, onValueChange = {author = it})
            Spacer(modifier = Modifier.size(16.dp))
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun AddScreenPreview() {
    PoetryAppTheme {
        AddScreen()
    }
}