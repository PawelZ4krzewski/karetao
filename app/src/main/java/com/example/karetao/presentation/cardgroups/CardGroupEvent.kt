package com.example.karetao.presentation.cardgroups

import com.example.karetao.data.use_case.cardGroup.CardGroupOrderType
import com.example.karetao.model.CardGroup

sealed class CardGroupEvent{
    data class Order(val cardGroupOrder: CardGroupOrderType): CardGroupEvent()
    data class DeleteCardGroup(val cardGroup: CardGroup): CardGroupEvent()
    object RestoreCardGroups: CardGroupEvent()
    object ToggleOrderSection: CardGroupEvent()
}
