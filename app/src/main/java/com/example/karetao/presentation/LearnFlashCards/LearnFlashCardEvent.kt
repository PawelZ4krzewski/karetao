package com.example.karetao.presentation.LearnFlashCards

sealed class LearnFlashCardEvent {
    data class SaveUserCard(val cardId: Int, val isCorrect: Boolean) : LearnFlashCardEvent()
}
