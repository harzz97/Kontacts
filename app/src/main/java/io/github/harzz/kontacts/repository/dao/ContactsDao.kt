package io.github.harzz.kontacts.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
    @Query("Delete from contacts_table where id = :id")
    fun deleteSingleContact(id : Int)

    //update the current user
    @Update
    fun updateSingleUser( contacts: Contacts)

}