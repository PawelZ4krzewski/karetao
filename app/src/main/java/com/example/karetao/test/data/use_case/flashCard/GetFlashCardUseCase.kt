package com.example.karetao.test.data.use_case.flashCard

import com.example.karetao.test.data.repositoryinterface.FlashCardRepository
import com.example.karetao.model.FlashCard
import javax.inject.Inject

class GetFlashCardUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    suspend operator fun invoke(id: Int): FlashCard? {
        return repository.getFlashCardById(id)
    }
}