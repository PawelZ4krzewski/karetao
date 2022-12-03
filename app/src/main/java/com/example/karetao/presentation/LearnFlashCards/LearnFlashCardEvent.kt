package com.example.karetao.presentation.LearnFlashCards

import com.example.karetao.model.FlashCard
import com.example.karetao.presentation.flashcards.FlashCardsEvent

sealed class LearnFlashCardEvent {
    data class SaveUserCard(val flashCard: FlashCard, val isCorrect: Boolean) : LearnFlashCardEvent()
}
