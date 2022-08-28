package com.example.karetao.model

import androidx.room.Entity

@Entity(tableName = "userGroup", primaryKeys = ["username","groupId"])
data class UserGroup(
    val username: String,
    val groupId: Int,
    val permission: String
)
