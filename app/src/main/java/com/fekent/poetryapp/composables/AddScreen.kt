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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.Saved
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.aboretoFont

@Composable
fun AddScreen(isAuthored: Boolean, onPoemEntered: (Authored?, Saved?)-> Unit) {
    AddScreenUI(isAuthored = isAuthored, onPoemEntered = onPoemEntered)
}

@Composable
fun AddScreenUI(isAuthored: Boolean, onPoemEntered: (Authored?, Saved?)-> Unit) {
    var poem by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }


    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(32.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Poem Title", fontFamily = aboretoFont, fontWeight = FontWeight.SemiBold) },
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
                label = { Text(text = "Author Name", fontFamily = aboretoFont, fontWeight = FontWeight.SemiBold) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
        }

        IconButton(onClick = {
            if(isAuthored){
                val newPoem = Authored(id = 0, title = title, poem = poem)
                onPoemEntered.invoke(newPoem, null)
            } else {
                val newPoem = Saved(id = 0, title = title, poem = poem, author = author)
                onPoemEntered.invoke(null, newPoem)
            }
        }) {
            Icon(
                Icons.Filled.AddCircle,
                "Save Button",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AddScreenPreview() {
    PoetryAppTheme {
        AddScreen(isAuthored = false, onPoemEntered ={_, _ -> })
    }
}