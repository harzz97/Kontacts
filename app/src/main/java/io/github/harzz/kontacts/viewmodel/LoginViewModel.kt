package io.github.harzz.kontacts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.harzz.kontacts.repository.UsersRepository
import io.github.harzz.kontacts.repository.entity.Users

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var usersRepository = UsersRepository(application)

    fun insert(users: Users) = usersRepository.insertNewUser(users)
}