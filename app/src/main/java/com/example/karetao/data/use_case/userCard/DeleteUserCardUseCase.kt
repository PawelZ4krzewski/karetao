package com.example.karetao.data.use_case.userCard

import com.example.karetao.data.repository.UserCardRepositoryImpl
import com.example.karetao.model.InvalidFlashCardException
import com.example.karetao.model.UserCard
import javax.inject.Inject

class DeleteUserCardUseCase @Inject constructor(private val repository: UserCardRepositoryImpl) {

    @Throws(InvalidFlashCardException::class)
    suspend operator fun invoke(userCard: UserCard){
        repository.deleteUserCard(userCard)
    }
}