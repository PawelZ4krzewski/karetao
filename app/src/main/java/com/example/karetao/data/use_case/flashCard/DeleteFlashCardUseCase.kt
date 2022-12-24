package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repositoryinterface.FlashCardRepository
import com.example.karetao.model.FlashCard
import javax.inject.Inject

class DeleteFlashCardUseCase @Inject constructor(
    private val repository: FlashCardRepository
){
    suspend operator fun invoke(flashCard: FlashCard){
        repository.deleteFlashCards(flashCard)
    }
}