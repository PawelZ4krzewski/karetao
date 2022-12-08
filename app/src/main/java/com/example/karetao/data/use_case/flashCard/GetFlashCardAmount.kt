package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repository.FlashCardRepository
import com.example.karetao.model.FlashCard
import javax.inject.Inject

class GetFlashCardAmount @Inject constructor(private val repository: FlashCardRepository) {

    suspend operator fun invoke(id: Int): Int {
        return repository.getAmountOfFlashCard(id)
    }
}