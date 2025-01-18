package com.fekent.poetryapp.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.Saved

@Composable
fun EditScreen(authoredPoem: Authored?, savedPoem: Saved?) {
    EditScreenUI(authoredPoem = authoredPoem, savedPoem = savedPoem)
}

@Composable
fun EditScreenUI(authoredPoem: Authored?, savedPoem: Saved?) {

}