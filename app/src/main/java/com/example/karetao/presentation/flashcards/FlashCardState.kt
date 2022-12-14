package com.example.karetao.presentation.flashcards

import com.example.karetao.data.use_case.flashCard.FlashCardOrderType
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.model.FlashCard

data class FlashCardState(
    val groupName: String = "Flash Card Group",
    val flashCards: List<FlashCard> = emptyList(),
    val flashCardOrder: FlashCardOrderType = FlashCardOrderType.Question(OrderType.Ascending),
    val groupId: Int = -1,
    val isOrderSectionVisible: Boolean = false
)