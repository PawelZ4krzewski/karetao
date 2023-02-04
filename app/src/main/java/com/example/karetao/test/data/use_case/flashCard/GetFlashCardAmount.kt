package com.example.karetao.test.data.use_case.flashCard

import com.example.karetao.test.data.repositoryinterface.FlashCardRepository
import javax.inject.Inject

class GetFlashCardAmount @Inject constructor(private val repository: FlashCardRepository) {

    suspend operator fun invoke(id: Int): Int {
        return repository.getAmountOfFlashCard(id)
    }
}