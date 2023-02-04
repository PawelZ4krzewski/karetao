package com.example.karetao.test.data.use_case.userCard

import com.example.karetao.test.data.repository.UserCardRepositoryImpl
import com.example.karetao.model.UserCard
import javax.inject.Inject

class GetUserCardUseCase @Inject constructor(
    private val repository: UserCardRepositoryImpl
) {
    suspend operator fun invoke(id: Int): UserCard? {
        return repository.getUserCardById(id)
    }
}