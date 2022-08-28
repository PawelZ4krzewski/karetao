package com.example.karetao.data.use_case

import com.example.karetao.data.repository.FlashCardRepository
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidFlashCardException

class AddFlashCard(
    private val repository: FlashCardRepository
) {
    @Throws(InvalidFlashCardException::class)
    suspend operator fun invoke(flashCard: FlashCard){
        if(flashCard.question.isBlank()){
            throw InvalidFlashCardException("The question of the flashcard can't be empty.")
        }

        if(flashCard.answer.isBlank()){
            throw InvalidFlashCardException("The answer of the flashcard can't be empty.")
        }

        repository.insertFlashCards(flashCard)
    }
}