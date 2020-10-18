package com.example.chatassignment.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//User table with User attributes
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    var userId: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "lastMessage")
    var lastMessage: String,
    @ColumnInfo(name = "lastMessageDate")
    var lastMessageDate: Date?,
    @ColumnInfo(name = "userImage")
    var userImage: Int
)
