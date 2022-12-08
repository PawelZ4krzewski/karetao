package com.example.karetao.data.use_case.userCard

import com.example.karetao.data.repository.UserCardRepository
import com.example.karetao.model.InvalidFlashCardException
import com.example.karetao.model.UserCard
import javax.inject.Inject

class AddUserCardUseCase @Inject constructor(private val repository: UserCardRepository) {

    @Throws(InvalidFlashCardException::class)
    suspend operator fun invoke(userCard: UserCard){
        repository.insertUserCard(userCard)
    }
}