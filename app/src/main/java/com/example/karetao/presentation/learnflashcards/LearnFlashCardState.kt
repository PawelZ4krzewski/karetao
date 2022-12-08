package com.example.karetao.presentation.learnflashcards


import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.flashCard.FlashCardOrderType
import com.example.karetao.model.FlashCard
import com.example.karetao.model.UserCard

data class LearnFlashCardState(
    val flashCards: List<FlashCard> = emptyList(),
    val learnedFlashCard: List<FlashCard> = emptyList(),
    val repeatedFlashCard: List<FlashCard> = emptyList(),
    val learningFlashCardSet: List<FlashCard> = emptyList(),
    val flashCardOrder: FlashCardOrderType = FlashCardOrderType.Question(OrderType.Ascending),
    val userCard: List<UserCard> = emptyList(),
    val username: String = "TEST"

)
