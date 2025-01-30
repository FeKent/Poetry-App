package com.fekent.poetryapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
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
fun AddScreen(
    isAuthored: Boolean?,
    poemToEdit: Pair<Authored?, Saved?>? = null,
    onPoemEntered: (Authored?, Saved?) -> Unit
) {
    val authoredPoem = poemToEdit?.first
    val savedPoem = poemToEdit?.second

    AddScreenUI(
        isAuthored = isAuthored,
        authoredPoem = authoredPoem,
        savedPoem = savedPoem,
        onPoemEntered = onPoemEntered
    )
}

@Composable
fun AddScreenUI(
    isAuthored: Boolean?,
    authoredPoem: Authored?,
    savedPoem: Saved?,
    onPoemEntered: (Authored?, Saved?) -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()

    var poem by remember { mutableStateOf(authoredPoem?.poem ?: savedPoem?.poem ?: "") }
    var title by remember { mutableStateOf(authoredPoem?.title ?: savedPoem?.title ?: "") }
    var author by remember { mutableStateOf(savedPoem?.author ?: "") }
    var translator by remember { mutableStateOf(savedPoem?.translator ?: "") }

    val largeGradient = object : ShaderBrush() {
        override fun createShader(size: androidx.compose.ui.geometry.Size): androidx.compose.ui.graphics.Shader {
            val center = Offset(size.width / 2f, size.height / 2f)
            val biggerDimension = maxOf(size.height, size.width)

            return RadialGradientShader(
                colors =
                if (!isDarkMode) {
                    listOf(
                        Color(0xFFCB7C65),  //Inner color
                        Color(0xFFEC9D7B),   // Intermediate color 1 (medium orange)
                        Color(0xFFF8D0A3),
                        Color(0xFFF9D0A5),   // Intermediate color 3 (soft peach)
                        Color(0xFFFFBBA7)   // Outer color
                    )
                } else {
                    listOf(
                        Color(0xFF857425),  //Inner color
                        Color(0xFF6A5C2D),   // Intermediate color 1 (dark yellow-brown)
                        Color(0xFF5B4A1C),   // Intermediate color 2 (dark amber-brown)
                        Color(0xFF4C3C0E),   // Intermediate color 3 (burnt brown-orange)
                        Color(0xFF4F4200), //Outer color
                    )
                },
                center = center,
                radius = biggerDimension / 1f,
                colorStops = listOf(0.1f, 0.7f, 1f, 1.2f, 2f)
            )
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(32.dp))
        TextField(
            textStyle = TextStyle(fontFamily = aboretoFont, fontWeight = FontWeight.SemiBold),
            value = title,
            onValueChange = { title = it },
            label = {
                Text(
                    text = "Poem Title",
                    fontFamily = aboretoFont,
                    fontWeight = FontWeight.SemiBold
                )
            },
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
                .background(largeGradient, RoundedCornerShape(10.dp))
        ) {
            BasicTextField(
                value = poem,
                onValueChange = { poem = it },
                textStyle = TextStyle(
                    fontFamily = abeezeeFont,
                    color = MaterialTheme.colorScheme.secondary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
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
                label = {
                    Text(
                        text = "Author's Name",
                        fontFamily = aboretoFont,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            TextField(
                textStyle = TextStyle(fontFamily = aboretoFont, fontWeight = FontWeight.SemiBold),
                value = translator,
                onValueChange = { translator = it },
                label = {
                    Text(
                        text = "Translator's Name",
                        fontFamily = aboretoFont,
                        fontWeight = FontWeight.SemiBold
                    )
                },
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
                val newPoem = Saved(
                    id = savedPoem.id,
                    title = title,
                    poem = poem,
                    author = author,
                    translator = translator.ifEmpty { null })
                onPoemEntered.invoke(null, newPoem)
            } else {
                // Adding a new poem (both Authored and Saved types)
                if ((isAuthored != null && isAuthored == false)) {
                    // If author is provided, we create a new Saved poem
                    val newPoem = Saved(
                        id = 0,
                        title = title,
                        poem = poem,
                        author = author,
                        translator = translator.ifEmpty { null }) // New Saved poem with id = 0
                    onPoemEntered.invoke(null, newPoem)
                } else {
                    // If author is not provided, create a new Authored poem
                    val newPoem = Authored(
                        id = 0,
                        title = title,
                        poem = poem
                    ) // New Authored poem with id = 0
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
        AddScreen(poemToEdit = null, onPoemEntered = { _, _ -> }, isAuthored = false)
    }
}