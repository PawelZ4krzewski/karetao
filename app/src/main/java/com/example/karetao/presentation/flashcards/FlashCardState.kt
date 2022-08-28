package com.example.karetao.presentation.flashcards

import com.example.karetao.data.use_case.FlashCardOrderType
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.model.FlashCard
import java.nio.ByteOrder

data class FlashCardState(
    val flashCards: List<FlashCard> = emptyList(),
    val flashCardOrder: FlashCardOrderType = FlashCardOrderType.Question(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)