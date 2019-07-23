package io.github.harzz.kontacts

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.harzz.kontacts.repository.entity.Contacts
import io.github.harzz.kontacts.utils.SharedPrefManager
import io.github.harzz.kontacts.viewmodel.ContactsViewModel
import kotlinx.android.synthetic.main.contacts_bottom_sheet_fragment.*


class ContactsBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = ContactsBottomSheet()
    }

    private lateinit var cnxt: Context
    private lateinit var viewModel: ContactsViewModel
    //lets assume that all contacts are new till we have arguments
    private var isNewContact : Boolean = true
    private lateinit var userName : String
    private lateinit var phoneNumber : String
    private lateinit var userId : String
    private lateinit var owner : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_bottom_sheet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //if arguments are present then its edit
        if(arguments != null && !arguments!!.isEmpty){
            btn_save.visibility = View.GONE
            btn_delete.visibility = View.VISIBLE
            userId = arguments!!.getString("id")
//            owner = arguments!!.getString("owner")
            userName = arguments!!.getString("user_name")
            phoneNumber = arguments!!.getString("user_phone_number")
            editText_user_name.setText(userName)
            editText_user_phone_number.setText(phoneNumber)
            txt_bottom_sheet_title.text = "Edit Users"
            isNewContact = false

        }

        viewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        owner = viewModel.owner

        btn_save.setOnClickListener {
            //validate username and phone number
            //check if new contact or existing one
            val (userName,phoneNumber) = getValuesFromTextBox()
            if(validateUserName(userName) && validateUserPhoneNumber(phoneNumber)){
                val contact = Contacts(owner,userName,phoneNumber)
                if(isNewContact) viewModel.insert(contact)
                else viewModel.updateSingleContact(contact)
                dismiss()
            }else{
                if(!validateUserPhoneNumber(phoneNumber))
                    editText_user_phone_number.error = "Enter valid phone number"
                if(!validateUserName(userName))
                    editText_user_name.error = "Pls enter username"
            }
        }

        //close the sheet
        btn_close_sheet.setOnClickListener {
            //TODO: create a method to get the current user
            //if the user is editing an existing contact
            if(!isNewContact){
                val (userName,phoneNumber) = getValuesFromTextBox()
                val contact = Contacts(owner,userName,phoneNumber)
                contact.id = userId.toInt()
                viewModel.updateSingleContact(contact)
            }
            dismiss()
        }

        btn_delete.setOnClickListener {
            val contact = Contacts(owner,userName,phoneNumber)
            contact.id = userId.toInt()
            viewModel.deleteSingleContact(contact)
            dismiss()

        }
    }

    private fun validateUserName(name : String): Boolean {
        return name.isNotEmpty() && name.isNotBlank() && name.length > 0
    }

    private fun validateUserPhoneNumber(number : String ) : Boolean{
        return  number.isNotEmpty() && number.isNotBlank() && number.isDigitsOnly() && number.length == 10
    }

    private fun getValuesFromTextBox():Pair<String,String>{
        val name = editText_user_name.editableText.trim().toString()
        val number = editText_user_phone_number.editableText.trim().toString()
        return Pair(name,number)
    }
}
