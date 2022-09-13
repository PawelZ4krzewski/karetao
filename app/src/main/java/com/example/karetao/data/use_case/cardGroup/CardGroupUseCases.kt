package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.use_case.flashCard.AddFlashCard
import com.example.karetao.data.use_case.flashCard.DeleteFlashCard
import com.example.karetao.data.use_case.flashCard.GetFlashCardUseCase
import com.example.karetao.data.use_case.flashCard.GetFlashCardsUseCase

data class CardGroupUseCases(
    val getCardGroup: GetCardGroupUseCase,
    val getCardGroups: GetCardGroupsUseCase,
    val deleteCardGroup: DeleteCardGroup,
    val addCardGroup: AddCardGroup
)