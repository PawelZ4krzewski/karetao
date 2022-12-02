package com.example.karetao.presentation.flashcards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.karetao.model.FlashCard
import com.example.karetao.ui.theme.White


@Composable
fun FlashCardItem(
    flashCard: FlashCard,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    onDeleteClick: () -> Unit
){
    Box(modifier = modifier
        .shadow(10.dp, RoundedCornerShape(cornerRadius))
    ){
        Row(modifier = Modifier
            .background(White, RoundedCornerShape(cornerRadius))
            .fillMaxWidth()
            .padding(20.dp, 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier
                .weight(0.8F)
                .fillMaxHeight()
            ){
                Text(
//                    modifier = Modifier.weight(1F),
                    text = flashCard.question,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
//                    modifier = Modifier.weight(1F),
                    text = flashCard.answer,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier
                    .weight(0.2F),
                onClick = onDeleteClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Flashcard"
                )
            }
        }
    }
}
