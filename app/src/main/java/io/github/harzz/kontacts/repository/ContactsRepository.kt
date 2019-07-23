package io.github.harzz.kontacts.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import io.github.harzz.kontacts.repository.dao.ContactsDao
import io.github.harzz.kontacts.repository.database.ContactsDatabase
import io.github.harzz.kontacts.repository.entity.Contacts
import io.github.harzz.kontacts.utils.SharedPrefManager

class ContactsRepository(val application: Application) {

    private var contactsDao : ContactsDao

//    private var contactsList : LiveData<List<Contacts>>

    init {
        val database : ContactsDatabase = ContactsDatabase.getInstance(application.applicationContext)
        contactsDao = database.contactsDao()
//        contactsList = contactsDao.getContacts("Harzz")

    }

    //function calls another async fun to perform the job
    fun insertContact(contacts: Contacts){
        InsertContactsAsyncTask(contactsDao).execute(contacts)
    }

    fun deleteContact(contacts: Contacts){
        DeleteContactsAsyncTask(contactsDao).execute(contacts)
    }

    fun updateContact(contacts: Contacts){
        UpdateContactsAsyncTask(contactsDao).execute(contacts)
    }

    fun getAllContacts() : LiveData<List<Contacts>>{
        val owner = SharedPrefManager(application.applicationContext).currentUserInfo
        return contactsDao.getContacts(owner!!);
    }

    private class InsertContactsAsyncTask(val contactsDao: ContactsDao) : AsyncTask<Contacts,Unit,Unit>(){
        override fun doInBackground(vararg p0: Contacts?) {
            contactsDao.insert(p0[0]!!)
        }
    }

    private class DeleteContactsAsyncTask(val contactsDao: ContactsDao) : AsyncTask<Contacts,Unit,Unit>(){

        override fun doInBackground(vararg p0: Contacts?) {
            contactsDao.deleteSingleContact(p0[0]!!)
        }
    }

    private class UpdateContactsAsyncTask(val contactsDao: ContactsDao) : AsyncTask<Contacts,Unit,Unit>(){
        override fun doInBackground(vararg p0: Contacts?) {
            contactsDao.updateSingleUser(p0[0]!!)
        }
    }

}