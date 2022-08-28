package com.example.karetao.data.use_case

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
