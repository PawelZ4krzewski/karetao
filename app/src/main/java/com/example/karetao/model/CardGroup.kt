package com.example.karetao.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardGroup")
data class CardGroup(
    @PrimaryKey(autoGenerate = true)
    val groupId: Int,
    val groupName: String
)
