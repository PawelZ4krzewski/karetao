package com.example.karetao.data.use_case

sealed class FlashCardOrderType(val orderType: OrderType) {
    class Question(orderType: OrderType): FlashCardOrderType(orderType)
    class Answer(orderType: OrderType): FlashCardOrderType(orderType)

    fun copy(orderType: OrderType): FlashCardOrderType{
        return when(this){
            is Question -> Question(orderType)
            is Answer -> Answer(orderType)
        }
    }
}
