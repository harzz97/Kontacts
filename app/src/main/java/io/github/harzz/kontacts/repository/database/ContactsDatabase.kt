package io.github.harzz.kontacts.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.harzz.kontacts.repository.dao.ContactsDao
import io.github.harzz.kontacts.repository.entity.Contacts
import io.github.harzz.kontacts.repository.entity.Users

@Database(entities = [Contacts::class, Users::class],version = 1,exportSchema = false)
abstract class ContactsDatabase : RoomDatabase(){

    abstract fun contactsDao() : ContactsDao

    companion object {
        private var instance: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {
            if (instance == null) {
                synchronized(ContactsDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDatabase::class.java,
                        "contacts_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }

    }
}
