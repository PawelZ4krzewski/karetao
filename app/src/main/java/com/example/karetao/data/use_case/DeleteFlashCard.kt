package com.example.karetao.data.use_case

import com.example.karetao.data.repository.FlashCardRepository
import com.example.karetao.model.FlashCard

class DeleteFlashCard(
    private val repository: FlashCardRepository
){
    suspend operator fun invoke(flashCard: FlashCard){
        repository.deleeteFlashCards(flashCard)
    }

}