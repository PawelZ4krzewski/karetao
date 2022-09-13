package com.example.karetao.presentation.cardgroups

import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.cardGroup.CardGroupOrderType
import com.example.karetao.model.CardGroup

data class CardGroupState (
    val cardGroups: List<CardGroup> = emptyList(),
    val cardGroupOrder: CardGroupOrderType = CardGroupOrderType.GroupName(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false

)
