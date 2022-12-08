package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.repository.CardGroupRepository
import com.example.karetao.model.CardGroup
import javax.inject.Inject

class DeleteCardGroupUseCase @Inject constructor(
    private val repository: CardGroupRepository
) {
    suspend operator fun invoke(cardGroup: CardGroup){
        repository.deleteCardGroup(cardGroup)
    }
}