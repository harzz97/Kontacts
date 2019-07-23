package io.github.harzz.kontacts.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.harzz.kontacts.repository.entity.Contacts

@Dao
interface ContactsDao {

    //insert a new contact
    @Insert
    fun insert(contacts: Contacts)

    //delete all contacts
    @Query("DELETE FROM contacts_table")
    fun deleteAllNotes()

    //return only users who belong to current_user
    @Query("SELECT * from contacts_table where owner = :owner ")
    fun getContacts(owner : String) : LiveData<List<Contacts>>

    //delete a particular contact
    @Delete
    fun deleteSingleContact(contacts: Contacts)

    //update the current user
    @Update
    fun updateSingleUser( contacts: Contacts)

}