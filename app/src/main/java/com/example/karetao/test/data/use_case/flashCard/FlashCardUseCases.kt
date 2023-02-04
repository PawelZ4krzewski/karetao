package com.example.karetao.test.data.use_case.flashCard

import javax.inject.Inject

data class FlashCardUseCases @Inject constructor(
    val getFlashCards: GetFlashCardsUseCase,
    val deleteFlashCard: DeleteFlashCardUseCase,
    val addFlashCard: AddFlashCardUseCase,
    val getFlashCard: GetFlashCardUseCase,
    val getFlashCardsFromSameGroupUseCase: GetFlashCardsFromSameGroupUseCase,
    val getFlashCardAmount: GetFlashCardAmount
)
