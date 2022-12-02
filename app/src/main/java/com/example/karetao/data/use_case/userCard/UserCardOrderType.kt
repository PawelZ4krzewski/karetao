package com.example.karetao.data.use_case.userCard

import com.example.karetao.data.use_case.OrderType

sealed class UserCardOrderType(val orderType: OrderType) {
    class LastEdit(orderType: OrderType) : UserCardOrderType(orderType)
    class Status(orderType: OrderType) : UserCardOrderType(orderType)

    fun copy(orderType: OrderType): UserCardOrderType {
        return when (this) {
            is LastEdit -> LastEdit(orderType)
            is Status -> Status(orderType)
        }
    }
}