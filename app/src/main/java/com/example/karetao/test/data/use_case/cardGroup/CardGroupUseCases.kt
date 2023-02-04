package com.example.karetao.test.data.use_case.cardGroup

import javax.inject.Inject

data class CardGroupUseCases @Inject constructor(
    val getCardGroup: GetCardGroupUseCase,
    val getCardGroups: GetCardGroupsUseCase,
    val deleteCardGroup: DeleteCardGroupUseCase,
    val addCardGroup: AddCardGroupUseCase
)