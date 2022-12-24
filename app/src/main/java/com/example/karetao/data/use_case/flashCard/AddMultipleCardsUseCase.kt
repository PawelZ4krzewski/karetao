package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repository.FlashCardRepositoryImpl
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidFlashCardException
import javax.inject.Inject

class AddMultipleCardsUseCase @Inject constructor(
    private val repository: FlashCardRepositoryImpl
) {
    @Throws(InvalidFlashCardException::class)
    suspend operator fun invoke(flashCards: List<FlashCard>){
        flashCards.forEach { flashCard ->
            if (flashCard.question.isBlank()) {
                throw InvalidFlashCardException("Any question of the flashcard can't be empty.")
            }

            if (flashCard.answer.isBlank()) {
                throw InvalidFlashCardException("Any answer of the flashcard can't be empty.")
            }
        }
        repository.insertMultipleFlashCards(flashCards)
    }
}