package com.example.karetao.data.use_case

data class FlashCardUseCases(
    val getFlashCards: GetFlashCardsUseCase,
    val deleteFlashCard: DeleteFlashCard,
    val addFlashCard: AddFlashCard,
    val getFlashCard: GetFlashCardUseCase
)
