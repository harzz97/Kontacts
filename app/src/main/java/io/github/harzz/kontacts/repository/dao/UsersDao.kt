package io.github.harzz.kontacts.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.harzz.kontacts.repository.entity.Users

@Dao
interface UsersDao{

    //insert a new user
    @Insert
    fun insert(users: Users)

    //validate user account
    @Query("Select * from users_table where userName = :userName ")
    fun getUserDetails(userName : String) : Users


    //delete user

}