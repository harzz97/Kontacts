package io.github.harzz.kontacts.repository.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.harzz.kontacts.repository.dao.ContactsDao
import io.github.harzz.kontacts.repository.entity.Contacts

@Database(entities = [Contacts::class],version = 1)
abstract class ContactsDatabase : RoomDatabase(){

    abstract fun contactsDao() : ContactsDao

    companion object {
        private lateinit var instance : ContactsDatabase

        fun getInstance(context: Context) : ContactsDatabase{
            if(instance == null){
                synchronized(ContactsDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDatabase::class.java,
                        "contacts_database")
                        .fallbackToDestructiveMigration()
//                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

//        fun getAllContacts() : LiveData<List<Contacts>> {}
    }

}