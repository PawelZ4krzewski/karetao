package com.example.karetao.test.data.use_case.cardGroup

import com.example.karetao.test.data.repository.CardGroupRepositoryImpl
import com.example.karetao.model.CardGroup
import javax.inject.Inject

class GetCardGroupUseCase @Inject constructor(
    private val repository: CardGroupRepositoryImpl
) {
    suspend operator fun invoke(id: Int): CardGroup? {
        return repository.getCardGroupById(id)
    }
}