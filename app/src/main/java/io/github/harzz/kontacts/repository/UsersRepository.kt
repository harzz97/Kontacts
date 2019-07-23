package io.github.harzz.kontacts.repository

import android.app.Application
import androidx.lifecycle.LiveData
import io.github.harzz.kontacts.repository.dao.UsersDao
import io.github.harzz.kontacts.repository.database.UserDatabase
import io.github.harzz.kontacts.repository.entity.Users

class UsersRepository(application: Application){

    private var usersDao : UsersDao
    var allUsers : LiveData<List<String>>

    init {
        val database:UserDatabase = UserDatabase.getInstance(application.applicationContext)
        usersDao = database.userDao()
        allUsers = usersDao.getAllUsers()
    }


    fun insertNewUser(users: Users) {
        Thread(Runnable {
            usersDao.insert(users).toInt()
        }).start()
    }

    fun getSingleUser(userEmail : String) : LiveData<Users>? {
        return usersDao.getUserDetails(userEmail)
    }

    fun getAllUser(): LiveData<List<String>>{
        return allUsers;
    }

}