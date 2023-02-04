package com.example.karetao.test.data.use_case

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
