package com.example.karetao.test.data.use_case.userCard

import javax.inject.Inject

data class UserCardUseCases @Inject constructor(
    val getUserCard: GetUserCardUseCase,
    val getUserCardFromSameUser: GetUserCardFromSameUserUseCase,
    val insertUserCard: AddUserCardUseCase,
    val deleteUserCard: DeleteUserCardUseCase
)
