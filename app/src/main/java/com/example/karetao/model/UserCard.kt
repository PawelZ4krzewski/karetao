package com.example.karetao.model

import androidx.room.Entity

@Entity(tableName = "userCard", primaryKeys = ["username", "cardId"])
data class UserCard(
    val username: String,
    val cardId: Int,
    val status: String

)
