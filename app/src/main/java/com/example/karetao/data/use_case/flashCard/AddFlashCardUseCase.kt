package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repositoryinterface.FlashCardRepository
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidFlashCardException
import javax.inject.Inject


class AddFlashCardUseCase @Inject constructor(
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