package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repositoryinterface.FlashCardRepository
import javax.inject.Inject

class GetFlashCardAmount @Inject constructor(private val repository: FlashCardRepository) {

    suspend operator fun invoke(id: Int): Int {
        return repository.getAmountOfFlashCard(id)
    }
}