package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.repository.CardGroupRepositoryImpl
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.model.CardGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCardGroupsUseCase @Inject constructor(
    private val repository: CardGroupRepositoryImpl
) {

    operator fun invoke(
        cardGroupOrderType: CardGroupOrderType = CardGroupOrderType.GroupName(OrderType.Descending)
    ): Flow<List<CardGroup>> {
        return repository.getCardGroups().map{ cardGroups ->
            when(cardGroupOrderType.orderType){
                is OrderType.Ascending -> cardGroups.sortedBy{it.groupName.lowercase()}
                is OrderType.Descending -> cardGroups.sortedByDescending{it.groupName.lowercase()}
            }
        }
    }

}