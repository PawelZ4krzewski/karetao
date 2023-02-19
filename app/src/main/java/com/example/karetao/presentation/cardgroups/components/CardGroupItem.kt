@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.karetao.presentation.flashcards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.karetao.model.CardGroupInformation
import com.example.karetao.ui.theme.White


@Composable
fun CardGroupItem(
    cardGroupInformation: CardGroupInformation,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(cornerRadius))
    ) {
        Row(
            modifier = Modifier
                .background(White, RoundedCornerShape(cornerRadius))
                .fillMaxWidth()
                .padding(20.dp, 30.dp)
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = cardGroupInformation.cardGroup.groupName,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.weight(1F),
                text = cardGroupInformation.amountOfFlashCard.toString(),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
            )
//            IconButton(
//                onClick = onDeleteClick
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Delete,
//                    contentDescription = "Delete Card Group")
//            }
        }
    }
}