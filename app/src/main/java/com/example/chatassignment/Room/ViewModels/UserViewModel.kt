package com.example.chatassignment.Room.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.chatassignment.Models.User
import com.example.chatassignment.Room.Repositories.UserRepository

//Viewmodel class to access data from repository
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: UserRepository =
        UserRepository(application)

    fun getUsers() = repository.getUsers()

    fun insertUser(user: User) {
        repository.insertUsers(user)
    }

    fun updateUser(user: User) = repository.updateUser(user)

}
