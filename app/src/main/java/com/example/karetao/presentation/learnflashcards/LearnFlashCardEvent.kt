package com.example.karetao.presentation.learnflashcards

import com.example.karetao.model.FlashCard

sealed class LearnFlashCardEvent {
    data class SaveUserCard(val flashCard: FlashCard, val isCorrect: Boolean) : LearnFlashCardEvent()
    data class LearnAgain(val isEveryFlashcards: Boolean) : LearnFlashCardEvent()


}
