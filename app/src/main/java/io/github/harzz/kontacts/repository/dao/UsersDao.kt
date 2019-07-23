package io.github.harzz.kontacts.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.harzz.kontacts.repository.entity.Users

@Dao
interface UsersDao{

    //insert a new user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(users: Users) : Long

    //validate user account
    @Query("Select * from users_table where email = :email ")
    fun getUserDetails( email : String) : LiveData<Users>

    @Query("Select email from users_table")
    fun getAllUsers() : LiveData<List<String>>

    //delete user
}