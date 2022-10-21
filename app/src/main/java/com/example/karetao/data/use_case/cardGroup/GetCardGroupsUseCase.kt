package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.repository.CardGroupRepository
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.model.CardGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCardGroupsUseCase(
    private val repository: CardGroupRepository
) {

    operator fun invoke(
        cardGroupOrderType: CardGroupOrderType = CardGroupOrderType.GroupName(OrderType.Descending)
    ): Flow<List<CardGroup>> {
        return repository.getCardGroups().map{ cardGroups ->
            when(cardGroupOrderType.orderType){
                is OrderType.Ascending -> cardGroups.sortedBy{it.groupName.lowercase()}
                is OrderType.Descending -> cardGroups.sortedBy{it.groupName.lowercase()}
            }
        }
    }

}