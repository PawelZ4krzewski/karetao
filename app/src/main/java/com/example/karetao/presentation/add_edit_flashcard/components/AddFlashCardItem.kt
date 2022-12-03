package com.example.karetao.presentation.add_edit_flashcard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.karetao.presentation.add_edit_flashcard.AddEditFlashCardEvent
import com.example.karetao.presentation.add_edit_flashcard.AddEditFlashCardViewModel
import com.example.karetao.presentation.add_edit_flashcard.FlashCardTextFieldState
import com.example.karetao.ui.theme.DarkBlue
import com.example.karetao.ui.theme.White

@Composable
fun AddFlashCardItem(
    questionState: FlashCardTextFieldState,
    answerState: FlashCardTextFieldState,
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
                .padding(30.dp)
        ) {
            TransparentHintTextField(
                text = questionState.text,
                hint = questionState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.EnteredQuestion(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.ChangeQuestionFocus(it))
                },
                isHintVisible = questionState.isHintVisible,
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
                text = answerState.text,
                hint = answerState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.EnteredAnswer(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.ChangeAnswerFocus(it))
                },
                isHintVisible = answerState.isHintVisible,
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