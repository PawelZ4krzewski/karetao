package com.example.karetao.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "flashCard"
//    foreignKeys = [
//        ForeignKey(entity = CardGroup::class, parentColumns = ["groupId"], childColumns = ["groupId"])
//    ]
)
data class FlashCard(
    @PrimaryKey val cardId: Int? = null,
    val groupId: Int? = null,
    val question: String,
    val answer: String

)

class InvalidFlashCardException(message: String): Exception(message)

