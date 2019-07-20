package io.github.harzz.kontacts.repository

import android.app.Application
import android.os.AsyncTask
import io.github.harzz.kontacts.repository.dao.UsersDao
import io.github.harzz.kontacts.repository.database.UserDatabase
import io.github.harzz.kontacts.repository.entity.Users

class UsersRepository(application: Application){

    private var usersDao : UsersDao

    init {
        val database:UserDatabase = UserDatabase.getInstance(application.applicationContext)
        usersDao = database.userDao()
    }

    fun insertNewUser(users: Users){
        InsertNewUserAsyncTask(usersDao).execute(users)
    }


    private class  InsertNewUserAsyncTask(val usersDao: UsersDao) : AsyncTask<Users,Unit,Unit>(){
        override fun doInBackground(vararg p0: Users?) {
            usersDao.insert(p0[0]!!)
        }
    }

}