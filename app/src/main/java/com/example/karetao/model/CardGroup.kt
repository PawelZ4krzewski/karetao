package com.example.karetao.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardGroup")
data class CardGroup(
    @PrimaryKey(autoGenerate = true)
    val groupId: Int? = null,
    val groupName: String,
    val description: String
)

class InvalidCardGroupException(message: String): Exception(message)

