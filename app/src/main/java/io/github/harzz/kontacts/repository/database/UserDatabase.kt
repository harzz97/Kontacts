package io.github.harzz.kontacts.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.harzz.kontacts.repository.dao.UsersDao
import io.github.harzz.kontacts.repository.entity.Users

@Database(entities = [Users::class],version = 1,exportSchema = false)
abstract class UserDatabase () : RoomDatabase(){

    abstract fun userDao() : UsersDao

    companion object{
        private var instance : UserDatabase? = null

        fun getInstance(context: Context) : UserDatabase{
            if(instance == null){
                synchronized(UserDatabase::class){
                    instance = Room.databaseBuilder(context,
                        UserDatabase::class.java,
                        "users_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }
}