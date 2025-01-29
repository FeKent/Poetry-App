package com.fekent.poetryapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.Saved
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.abeezeeFont
import com.fekent.poetryapp.ui.theme.aboretoFont

@Composable
fun AddScreen(isAuthored: Boolean?, poemToEdit: Pair<Authored?, Saved?>? = null, onPoemEntered: (Authored?, Saved?)-> Unit) {
    val authoredPoem = poemToEdit?.first
    val savedPoem = poemToEdit?.second

    AddScreenUI(isAuthored = isAuthored, authoredPoem = authoredPoem, savedPoem = savedPoem, onPoemEntered = onPoemEntered)
}

@Composable
fun AddScreenUI(isAuthored: Boolean?, authoredPoem: Authored?, savedPoem: Saved?, onPoemEntered: (Authored?, Saved?)-> Unit) {

    var poem by remember { mutableStateOf(authoredPoem?.poem ?: savedPoem?.poem ?: "") }
    var title by remember { mutableStateOf(authoredPoem?.title ?: savedPoem?.title ?: "") }
    var author by remember { mutableStateOf(savedPoem?.author ?:"") }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(32.dp))
        TextField(
            textStyle = TextStyle(fontFamily = aboretoFont, fontWeight = FontWeight.SemiBold),
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

        Box(
            modifier = Modifier
                .padding(horizontal = 56.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)

                .imePadding() // Adjust layout when the keyboard appears
        ) {
            BasicTextField(
                value = poem,
                onValueChange = { poem = it },
                textStyle = TextStyle(fontFamily = abeezeeFont), // Customize your font here
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()) // Enable scrolling when content overflows
                    .heightIn(max = 200.dp) // Set the maximum height for the TextField
            )
        }

        Spacer(modifier = Modifier.size(16.dp))
        if (isAuthored != null && isAuthored == false) {
            TextField(
                textStyle = TextStyle(fontFamily = aboretoFont, fontWeight = FontWeight.SemiBold),
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
            if (authoredPoem != null) {
                // Editing an existing Authored poem
                val newPoem = Authored(id = authoredPoem.id, title = title, poem = poem)
                onPoemEntered.invoke(newPoem, null)
            } else if (savedPoem != null) {
                // Editing an existing Saved poem
                val newPoem = Saved(id = savedPoem.id, title = title, poem = poem, author = author)
                onPoemEntered.invoke(null, newPoem)
            } else {
                // Adding a new poem (both Authored and Saved types)
                if ((isAuthored != null && isAuthored == false)) {
                    // If author is provided, we create a new Saved poem
                    val newPoem = Saved(id = 0, title = title, poem = poem, author = author) // New Saved poem with id = 0
                    onPoemEntered.invoke(null, newPoem)
                } else {
                    // If author is not provided, create a new Authored poem
                    val newPoem = Authored(id = 0, title = title, poem = poem) // New Authored poem with id = 0
                    onPoemEntered.invoke(newPoem, null)
                }
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
        AddScreen(poemToEdit = null, onPoemEntered ={_, _ -> }, isAuthored = false)
    }
}