package io.github.harzz.kontacts.repository.database

import android.content.Context
import android.os.AsyncTask
import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.harzz.kontacts.repository.dao.ContactsDao
import io.github.harzz.kontacts.repository.entity.Contacts

@Database(entities = [Contacts::class],version = 1,exportSchema = false)
abstract class ContactsDatabase : RoomDatabase(){

    abstract fun contactsDao() : ContactsDao

    companion object {
        private var instance : ContactsDatabase? = null

        fun getInstance(context: Context) : ContactsDatabase{
            if(instance == null){
                synchronized(ContactsDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsDatabase::class.java,
                        "contacts_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance!!
        }

//        fun getAllContacts() : LiveData<List<Contacts>> {}
        private val roomCallback  = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }



}
class PopulateDbAsyncTask(db: ContactsDatabase?) : AsyncTask<Unit, Unit, Unit>() {

    private val contactsDao = db?.contactsDao()

    override fun doInBackground(vararg p0: Unit?) {
        contactsDao?.insert(Contacts("Harzz","Elliot","9444974476"))
        contactsDao?.insert(Contacts("Harzz","Wallice","9444985587"))
        contactsDao?.insert(Contacts("Suresh","IronMan","9444974471"))
        contactsDao?.insert(Contacts("Harzz","Thor","9444974473"))
        contactsDao?.insert(Contacts("ramesh","Suresh","9444974479"))
        contactsDao?.insert(Contacts("Suresh","Ramesh","9444974474"))
        contactsDao?.insert(Contacts("Suresh","Gokul","94449744722"))
        contactsDao?.insert(Contacts("Suresh","Rakesh","944497447433"))
        contactsDao?.insert(Contacts("Suresh","Ramess","9444974474124"))
    }


}