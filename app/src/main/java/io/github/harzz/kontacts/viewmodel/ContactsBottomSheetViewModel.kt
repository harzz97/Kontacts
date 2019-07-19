package io.github.harzz.kontacts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel;
import io.github.harzz.kontacts.repository.ContactsRepository
import io.github.harzz.kontacts.repository.entity.Contacts

class ContactsBottomSheetViewModel(application: Application) : AndroidViewModel(application) {

    private var contactsRepository = ContactsRepository(application)

    fun createContact(contact: Contacts) = contactsRepository.insertContact(contact)

    fun updateContact(contact: Contacts) = contactsRepository.updateContact(contact)

    fun deleteContact(contact: Contacts) = contactsRepository.deleteContact(contact)

}
