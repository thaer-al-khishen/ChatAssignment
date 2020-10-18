package com.example.chatassignment.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Message table with message attributes
@Entity(tableName = "message_table")
data class Message(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,

    @ColumnInfo(name = "userIdToBeCopied") var userId: Int,

    @ColumnInfo(name = "message")
    var message: String,

    @ColumnInfo(name = "date")
    var date: Date?

)
