package com.example.karetao.presentation.addeditflashcard

data class FlashCardTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)