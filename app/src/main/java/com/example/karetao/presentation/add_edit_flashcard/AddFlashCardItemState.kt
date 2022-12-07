package com.example.karetao.presentation.add_edit_flashcard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

data class AddFlashCardItemState(

    val flashCardQuestion: MutableState<FlashCardTextFieldState> =
    mutableStateOf(FlashCardTextFieldState(
    hint = "Enter question..."
    )),

    val flashCardAnswer: MutableState<FlashCardTextFieldState> = mutableStateOf(FlashCardTextFieldState(
        hint = "Enter answer..."
    ))

)