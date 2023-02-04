package com.example.karetao.test.data.use_case.flashCard

import com.example.karetao.test.data.repositoryinterface.FlashCardRepository
import com.example.karetao.test.data.use_case.OrderType
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFlashCardsFromSameGroupUseCase @Inject constructor(
    private val repository: FlashCardRepository
    ) {
        operator fun invoke(
            id: Int,
            flashCardOrderType: FlashCardOrderType = FlashCardOrderType.Question(OrderType.Descending)
        ): Flow<List<FlashCard>> {
            return repository.getFlashCardsFromSameGroup(id).map { flashCards ->
                when (flashCardOrderType.orderType) {
                    is OrderType.Ascending -> {
                        when (flashCardOrderType) {
                            is FlashCardOrderType.Question -> flashCards.sortedBy { it.question.lowercase() }
                            is FlashCardOrderType.Answer -> flashCards.sortedBy { it.answer.lowercase() }
                        }
                    }
                    is OrderType.Descending -> {
                        when (flashCardOrderType) {
                            is FlashCardOrderType.Question -> flashCards.sortedByDescending { it.question.lowercase() }
                            is FlashCardOrderType.Answer -> flashCards.sortedByDescending { it.answer.lowercase() }
                        }
                    }
                }
            }
        }
}