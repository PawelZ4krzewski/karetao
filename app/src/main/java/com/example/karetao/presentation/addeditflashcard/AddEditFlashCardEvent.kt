package com.example.karetao.presentation.addeditflashcard

import androidx.compose.ui.focus.FocusState

sealed class AddEditFlashCardEvent {
    data class EnteredQuestion(val value: String, val id: Int): AddEditFlashCardEvent()
    data class ChangeQuestionFocus(val focusState: FocusState, val id: Int): AddEditFlashCardEvent()
    data class EnteredAnswer(val value: String, val id: Int): AddEditFlashCardEvent()
    data class ChangeAnswerFocus(val focusState: FocusState, val id: Int): AddEditFlashCardEvent()
    data class DeleteFlashCardItem(val item: AddFlashCardItemValues): AddEditFlashCardEvent()

    object AddNewFlashCardItem: AddEditFlashCardEvent()
    object SaveFlashCard: AddEditFlashCardEvent()
}