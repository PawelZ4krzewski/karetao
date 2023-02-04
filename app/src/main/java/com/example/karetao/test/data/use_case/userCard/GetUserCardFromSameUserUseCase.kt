package com.example.karetao.test.data.use_case.userCard

import com.example.karetao.test.data.repository.UserCardRepositoryImpl
import com.example.karetao.test.data.use_case.OrderType
import com.example.karetao.model.UserCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserCardFromSameUserUseCase @Inject constructor(
    private val repository: UserCardRepositoryImpl
) {
    operator fun invoke(
        username: String,
        userCardOrderType: UserCardOrderType = UserCardOrderType.LastEdit(OrderType.Descending)
    ): Flow<List<UserCard>> {
        return repository.getCardFromSameUser(username).map { usercards ->
            when (userCardOrderType.orderType) {
                is OrderType.Ascending -> {
                    when (userCardOrderType) {
                        is UserCardOrderType.Status -> usercards.sortedBy { it.status }
                        is UserCardOrderType.LastEdit -> usercards.sortedBy { it.lastEdit }
                    }
                }
                is OrderType.Descending -> {
                    when (userCardOrderType) {
                        is UserCardOrderType.Status -> usercards.sortedByDescending { it.status}
                        is UserCardOrderType.LastEdit -> usercards.sortedByDescending { it.lastEdit}
                    }
                }
            }
        }
    }
}