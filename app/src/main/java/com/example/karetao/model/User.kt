package com.example.karetao.model

import androidx.room.AutoMigration
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val username: String,
    val password: String
)
