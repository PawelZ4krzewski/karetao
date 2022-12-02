package com.example.karetao.data.use_case.flashCard

data class FlashCardUseCases(
    val getFlashCards: GetFlashCardsUseCase,
    val deleteFlashCard: DeleteFlashCard,
    val addFlashCard: AddFlashCard,
    val getFlashCard: GetFlashCardUseCase,
    val getFlashCardsFromSameGroupUseCase: GetFlashCardsFromSameGroupUseCase,
    val getFlashCardAmount: GetFlashCardAmount
)
