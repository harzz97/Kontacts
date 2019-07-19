package io.github.harzz.kontacts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ContactsBottomSheet : Fragment() {

    companion object {
        fun newInstance() = ContactsBottomSheet()
    }

    private lateinit var viewModel: ContactsBottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_bottom_sheet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactsBottomSheetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
