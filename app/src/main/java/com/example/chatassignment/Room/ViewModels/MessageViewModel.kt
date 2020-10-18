package com.example.chatassignment.Room.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.chatassignment.Models.Message
import com.example.chatassignment.Room.Repositories.MessageRepository

//Viewmodel class to access data from repository
class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: MessageRepository =
        MessageRepository(application)

    fun getMessages(id: Int) = repository.getMessages(id)

    fun setMessage(message: Message) {
        repository.insertMessage(message)
    }

    fun deleteAll(id: Int) = repository.deleteMessages(id)

}
