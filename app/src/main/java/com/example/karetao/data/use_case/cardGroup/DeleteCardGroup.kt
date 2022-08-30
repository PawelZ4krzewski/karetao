package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.repository.CardGroupRepository
import com.example.karetao.model.CardGroup
import com.example.karetao.model.FlashCard

class DeleteCardGroup(
    private val repository: CardGroupRepository
) {
    suspend operator fun invoke(cardGroup: CardGroup){
        repository.deleteCardGroup(cardGroup)
    }
}