package com.example.karetao.presentation.addeditflashcard

data class AddFlashCardItemValues(

    val flashCardQuestionText: String = "",
    val flashCardQuestionHint: String = "Enter question...",
    val isFlashCardQuestionVisible: Boolean = false,

    val flashCardAnswerText: String = "",
    val flashCardAnswerHint: String = "Enter answer...",
    val isFlashCardAnswerVisible: Boolean = true,

)