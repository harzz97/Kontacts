package io.github.harzz.kontacts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.harzz.kontacts.repository.ContactsRepository
import io.github.harzz.kontacts.repository.entity.Contacts
import io.github.harzz.kontacts.utils.SharedPrefManager

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private var contactsRepository = ContactsRepository(application)

    private var allContacts = contactsRepository.getAllContacts()

    fun insert(contacts: Contacts) = contactsRepository.insertContact(contacts)

    fun updateSingleContact(contacts: Contacts) = contactsRepository.updateContact(contacts)

    fun deleteSingleContact( contacts: Contacts) = contactsRepository.deleteContact(contacts)

    fun getAllContacts() : LiveData<List<Contacts>> = allContacts

    var owner : String = SharedPrefManager(application).currentUserInfo!!
}