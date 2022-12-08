package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repository.FlashCardRepository
import com.example.karetao.model.FlashCard
import javax.inject.Inject

class GetFlashCardUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    suspend operator fun invoke(id: Int): FlashCard? {
        return repository.getFlashCardById(id)
    }
}