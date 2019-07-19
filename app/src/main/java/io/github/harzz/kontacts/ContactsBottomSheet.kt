package io.github.harzz.kontacts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.harzz.kontacts.repository.entity.Contacts
import io.github.harzz.kontacts.viewmodel.ContactsBottomSheetViewModel
import io.github.harzz.kontacts.viewmodel.ContactsViewModel
import kotlinx.android.synthetic.main.contacts_bottom_sheet_fragment.*


class ContactsBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = ContactsBottomSheet()
    }

    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_bottom_sheet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //if arguments are present then its edit
        if(!arguments!!.isEmpty){
            editText_user_name.setText(arguments!!.getString("user_name"))
            editText_user_phone_number.setText(arguments!!.getString("user_phone_number"))
            txt_bottom_sheet_title.setText("Edit User")
        }

        viewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)

        btn_save.setOnClickListener {
            //if its new contact
            if(validateUserName() && validateUserPhoneNumber()){
                Log.v("CBS","VALIDUSERNAME")
                val name = editText_user_name.editableText.trim().toString()
                val number = editText_user_phone_number.editableText.trim().toString()
                val contact = Contacts("Suresh",name,number)
                viewModel.insert(contact)
            }else{
                Log.v("CBS","INVALID")
            }

            //close the sheet
            btn_close_sheet.setOnClickListener {
                dismiss()
            }

        }

    }

    private fun validateUserName(): Boolean {
        val name = editText_user_name.editableText.trim().toString()
        return name.isNotEmpty() && name.isNotBlank() && name.length > 0
    }

    private fun validateUserPhoneNumber() : Boolean{
        val number = editText_user_phone_number.editableText.trim().toString()
        return  number.isNotEmpty() && number.isNotBlank() && number.isDigitsOnly()
    }
}
