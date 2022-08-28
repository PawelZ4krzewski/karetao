package com.example.karetao.presentation.flashcards.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.karetao.model.FlashCard

@Composable
fun FlashCardItem(
    flashCard: FlashCard,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp
){
    Box(modifier = modifier
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(cornerRadius))
        ){
             Text(
                 modifier = Modifier.weight(1F),
                 text = flashCard.question,
                 style = MaterialTheme.typography.h6,
                 color = MaterialTheme.colors.onSurface,
                 textAlign = TextAlign.Center,
                 overflow = TextOverflow.Ellipsis
             )
             Text(
                 modifier = Modifier.weight(1F),
                 text = flashCard.answer,
                 style = MaterialTheme.typography.h6,
                 color = MaterialTheme.colors.onSurface,
                 textAlign = TextAlign.Center,
                 overflow = TextOverflow.Ellipsis
             )
        }
    }
}