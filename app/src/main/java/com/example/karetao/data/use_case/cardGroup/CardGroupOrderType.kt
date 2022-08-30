package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.use_case.OrderType

sealed class CardGroupOrderType(val orderType: OrderType){
    class groupName(orderType: OrderType): CardGroupOrderType(orderType)

    fun copy(orderType: OrderType): CardGroupOrderType {
        return groupName(orderType)
        }

}

