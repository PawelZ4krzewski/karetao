package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.repository.CardGroupRepository
import com.example.karetao.model.CardGroup
import com.example.karetao.model.FlashCard
import javax.inject.Inject

class GetCardGroupUseCase @Inject constructor(
    private val repository: CardGroupRepository
) {
    suspend operator fun invoke(id: Int): CardGroup? {
        return repository.getCardGroupById(id)
    }
}