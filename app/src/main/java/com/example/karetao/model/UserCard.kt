package com.example.karetao.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Entity(tableName = "userCard", primaryKeys = ["username", "cardId"])
@RequiresApi(Build.VERSION_CODES.O)
data class UserCard constructor(
    val username: String,
    val cardId: Int,
    val status: Int,
    val lastEdit: Long? = System.currentTimeMillis()
)