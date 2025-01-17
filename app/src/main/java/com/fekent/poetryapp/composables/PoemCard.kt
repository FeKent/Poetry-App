package com.fekent.poetryapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.poetryapp.data.Authored
import com.fekent.poetryapp.data.Saved
import com.fekent.poetryapp.ui.theme.PoetryAppTheme
import com.fekent.poetryapp.ui.theme.abeezeeFont
import com.fekent.poetryapp.ui.theme.aboretoFont

@Composable
fun PoemCard(
    authored: Authored?,
    saved: Saved?,
) {
    val isDarkMode = isSystemInDarkTheme()

    val largeGradient = object : ShaderBrush() {
        override fun createShader(size: androidx.compose.ui.geometry.Size): androidx.compose.ui.graphics.Shader {
            val center = Offset(size.width / 2f, size.height / 2f)
            val biggerDimension = maxOf(size.height, size.width)


            return RadialGradientShader(
                colors =
                if (!isDarkMode){
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

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier

            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp,
                spotColor = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp)
            )
            .background(largeGradient, RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.size(16.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 16.dp).weight(1f).align(Alignment.CenterVertically))
                if (authored != null || saved != null) {
                    (authored?.title ?: saved?.title)?.let {
                        Text(
                            text = it,
                            fontFamily = aboretoFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.weight(2f).padding(end = 2.dp, start = 2.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end= 16.dp).weight(1f).align(Alignment.CenterVertically))
            }

            Spacer(Modifier.size(16.dp))
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (authored != null || saved != null) {
                    (authored?.poem ?: saved?.poem)?.let {
                        Text(
                            text = it,
                            fontFamily = abeezeeFont,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
            Spacer(Modifier.size(16.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 16.dp).weight(1f).align(Alignment.CenterVertically))
                if (saved != null) {
                    Text(
                        text = saved.author,
                        fontFamily = aboretoFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.weight(1.5f).padding(end = 2.dp, start = 2.dp),
                        textAlign = TextAlign.Center
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end= 16.dp).weight(1f).align(Alignment.CenterVertically))
            }
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.Top) {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Edit, "edit", tint = MaterialTheme.colorScheme.secondary)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Delete, "Delete", tint = MaterialTheme.colorScheme.secondary)
                }
            }




            // Spacer(Modifier.size(16.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PoemScreenPreview() {
    PoetryAppTheme {
        PoemCard(
            authored = null, saved = Saved(
                0, "There once was a boy named Ryan", "Some say the world will end in fire,\n" +
                        "Some say in ice.\n" +
                        "From what I’ve tasted of desire\n" +
                        "I hold with those who favor fire.\n" +
                        "But if it had to perish twice,\n" +
                        "I think I know enough of hate\n" +
                        "To say that for destruction ice\n" +
                        "Is also great\n" +
                        "And would suffice.", "Reginald Fortescu the Third"
            )
        )
    }
}