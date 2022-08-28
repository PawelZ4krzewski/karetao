package com.example.karetao.presentation.add_edit_flashcard

import androidx.compose.ui.focus.FocusState

sealed class AddEditFlashCardEvent {
    data class EnteredQuestion(val value: String): AddEditFlashCardEvent()
    data class ChangeQuestionFocus(val focusState: FocusState): AddEditFlashCardEvent()
    data class EnteredAnswer(val value: String): AddEditFlashCardEvent()
    data class ChangeAnswerFocus(val focusState: FocusState): AddEditFlashCardEvent()

    object SaveFlashCard: AddEditFlashCardEvent()
}