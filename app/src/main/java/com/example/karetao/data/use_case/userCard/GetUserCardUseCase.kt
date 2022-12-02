package com.example.karetao.data.use_case.userCard

import com.example.karetao.data.repository.UserCardRepository
import com.example.karetao.model.UserCard

class GetUserCardUseCase(
    private val repository: UserCardRepository
) {
    suspend operator fun invoke(id: Int): UserCard? {
        return repository.getUserCardById(id)
    }
}