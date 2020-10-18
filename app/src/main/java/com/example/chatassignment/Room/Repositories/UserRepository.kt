package com.example.chatassignment.Room.Repositories

import android.app.Application
import com.example.chatassignment.Models.User
import com.example.chatassignment.Room.DAOs.UserDao
import com.example.chatassignment.Room.Database.ApplicationDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserRepository(application: Application) : CoroutineScope {

    //Enable use of coroutines
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    //Instantiate UserDao
    private var userDao: UserDao?

    init {
        val db =
            ApplicationDatabase.getDatabase(
                application
            )
        userDao = db?.userDao()
    }

    //Use Coroutines for concurrent programming when updating users
    fun updateUser(user: User) {
        launch { updateUserBG(user) }
    }

    //Execute function on another thread
    private suspend fun updateUserBG(user: User) {
        withContext(Dispatchers.IO) {
            userDao?.updateUser(user)
        }
    }

    //Retrieve users from DAO
    fun getUsers() = userDao?.getUsers()

    //Use Coroutines for concurrent programming when inserting users
    fun insertUsers(user: User) {
        launch { insertUserBG(user) }
    }

    //Execute function on another thread
    private suspend fun insertUserBG(user: User) {
        withContext(Dispatchers.IO) {
            userDao?.insertUser(user)
        }
    }

}
