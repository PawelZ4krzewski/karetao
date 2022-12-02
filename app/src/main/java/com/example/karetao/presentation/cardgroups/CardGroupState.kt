package com.example.karetao.presentation.cardgroups

import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.cardGroup.CardGroupOrderType
import com.example.karetao.model.CardGroup
import com.example.karetao.model.CardGroupInformation

data class CardGroupState (
    val cardGroupsInformation: List<CardGroupInformation> = emptyList(),
    val cardGroupOrder: CardGroupOrderType = CardGroupOrderType.GroupName(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false,
)
