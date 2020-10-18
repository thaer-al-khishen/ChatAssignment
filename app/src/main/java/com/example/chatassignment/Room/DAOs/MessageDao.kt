package com.example.chatassignment.Room.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chatassignment.Models.Message

//Database functions for message defined in MessageDao before being used in MessageRepository
@Dao
interface MessageDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(movie: Message)

    @Transaction
    @Query("SELECT * from message_table where userIdToBeCopied= :userId ORDER BY date ASC")
    fun getMessages(userId: Int): LiveData<List<Message>>

    @Query("DELETE FROM message_table where userIdToBeCopied= :userId")
    fun deleteAll(userId: Int)

}
