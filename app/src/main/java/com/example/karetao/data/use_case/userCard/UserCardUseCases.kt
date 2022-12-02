package com.example.karetao.data.use_case.userCard

import com.example.karetao.data.repository.UserCardRepository

data class UserCardUseCases(
    val getUserCard: GetUserCardUseCase,
    val getUserCardFromSameUser: GetUserCardFromSameUserUseCase,
    val insertUserCard: AddUserCard,
    val deleteUserCard: DeleteUserCard
)
