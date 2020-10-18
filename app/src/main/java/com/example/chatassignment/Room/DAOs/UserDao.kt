package com.example.chatassignment.Room.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chatassignment.Models.User

//Database functions for user defined in UserDao before being used in UserRepository
@Dao
interface UserDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Transaction
    @Query("SELECT * from user_table ORDER BY lastMessageDate DESC")
    fun getUsers(): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    fun deleteAll()

    @Transaction
    @Update
    fun updateUser(user: User)
}
