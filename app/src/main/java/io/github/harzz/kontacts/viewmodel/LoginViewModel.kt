package io.github.harzz.kontacts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.github.harzz.kontacts.repository.UsersRepository
import io.github.harzz.kontacts.repository.entity.Users

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var usersRepository = UsersRepository(application)
    fun createNewUser(users: Users)  {
        usersRepository.insertNewUser(users)
    }

    fun getUserDetails(currentUser : Users) : LiveData<Users>?{
        return usersRepository.getSingleUser(currentUser.email)
    }
    fun getAllUsers() : LiveData<List<String>>?{
        return usersRepository.getAllUser()
    }
}
