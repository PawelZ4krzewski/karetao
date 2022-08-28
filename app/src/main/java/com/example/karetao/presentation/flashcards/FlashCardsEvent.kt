package com.example.karetao.presentation.flashcards

import com.example.karetao.data.use_case.FlashCardOrderType
import com.example.karetao.model.FlashCard

sealed class FlashCardsEvent {
    data class Order(val flashCardOrder: FlashCardOrderType): FlashCardsEvent()
    data class DeleteFlashCard(val flashCard: FlashCard): FlashCardsEvent()
    object RestoreFlashCard: FlashCardsEvent()
    object ToggleOrderSection: FlashCardsEvent()
}
