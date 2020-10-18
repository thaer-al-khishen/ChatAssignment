package com.example.chatassignment.Room.Repositories

import android.app.Application
import com.example.chatassignment.Models.Message
import com.example.chatassignment.Room.DAOs.MessageDao
import com.example.chatassignment.Room.Database.ApplicationDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MessageRepository(application: Application) : CoroutineScope {

    //Enable the use of coroutines
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    //Instantiate MessageDao
    private var messageDao: MessageDao?

    init {
        val db =
            ApplicationDatabase.getDatabase(
                application
            )
        messageDao = db?.messageDao()
    }

    //Retrieve messages from MessageDao
    fun getMessages(id: Int) = messageDao?.getMessages(id)

    //Use Coroutines for concurrent programming when deleting users
    fun deleteMessages(id: Int) {
        launch { deleteMessageBG(id) }
    }

    //Execute function on another thread
    private suspend fun deleteMessageBG(id: Int) {
        withContext(Dispatchers.IO) {
            messageDao?.deleteAll(id)
        }
    }

    //Use Coroutines for concurrent programming when inserting messages
    fun insertMessage(message: Message) {
        launch { insertMessageBG(message) }
    }

    //Execute function on another thread
    private suspend fun insertMessageBG(message: Message) {
        withContext(Dispatchers.IO) {
            messageDao?.insertMessage(message)
        }
    }

}
