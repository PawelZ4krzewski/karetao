package com.example.karetao.presentation.add_edit_flashcard

data class FlashCardTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)