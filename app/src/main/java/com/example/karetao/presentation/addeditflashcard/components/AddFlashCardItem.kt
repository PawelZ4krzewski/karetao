package com.example.karetao.presentation.addeditflashcard.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.karetao.presentation.addeditflashcard.AddEditFlashCardEvent
import com.example.karetao.presentation.addeditflashcard.AddEditFlashCardViewModel
import com.example.karetao.presentation.addeditflashcard.AddFlashCardItemValues
import com.example.karetao.ui.theme.DarkBlue
import com.example.karetao.ui.theme.White

@Composable
fun AddFlashCardItem(
    addFlashCardItemValues: AddFlashCardItemValues,
    index: Int,
    onDeleteClick: () -> Unit,
    viewModel: AddEditFlashCardViewModel  = hiltViewModel()
)
{
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .background(White, RoundedCornerShape(10.dp))
                .padding(
                    start = 30.dp,
                    top = 30.dp,
                    end = 30.dp,
                    bottom = 30.dp
                )
        ) {

            if(index > 0){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .offset(y=(-25).dp, x = (25).dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onDeleteClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Flashcard"
                        )
                    }
                }
            }

            TransparentHintTextField(
                text = addFlashCardItemValues.flashCardQuestionText,
                hint = addFlashCardItemValues.flashCardQuestionHint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.EnteredQuestion(it,index))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.ChangeQuestionFocus(it,index))
                },
                isHintVisible = addFlashCardItemValues.isFlashCardQuestionVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h6
            )
            Spacer(
                modifier = Modifier
                    .padding(
                        top = 5.dp,
                        bottom = 5.dp
                    )
                    .height(4.dp)
                    .fillMaxWidth()
                    .background(DarkBlue)
            )

            Text(
                text = "QUESTION",
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(20.dp))

            TransparentHintTextField(
                text = addFlashCardItemValues.flashCardAnswerText,
                hint = addFlashCardItemValues.flashCardAnswerHint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.EnteredAnswer(it,index))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.ChangeAnswerFocus(it,index))
                },
                isHintVisible = addFlashCardItemValues.isFlashCardAnswerVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h6
            )

            Spacer(
                modifier = Modifier
                    .padding(
                        top = 5.dp,
                        bottom = 5.dp
                    )
                    .height(4.dp)
                    .fillMaxWidth()
                    .background(DarkBlue)
            )

            Text(
                text = "ANSWER",
                style = MaterialTheme.typography.body1
            )
        }
    }
}