package io.github.harzz.kontacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.harzz.kontacts.R
import io.github.harzz.kontacts.repository.entity.Contacts
import kotlinx.android.synthetic.main.content_row_recycler_view.view.*

class ContactsAdapter(val listener : (Contacts) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private var allContacts : List<Contacts> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.content_row_recycler_view,parent,false)
        return ContactsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allContacts.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {

        val currentContact = allContacts.get(position)
        holder.userName.text = currentContact.user_name
        holder.userPhoneNumber.text = currentContact.user_phone_number

        holder.itemView.setOnClickListener {
            listener(currentContact)
        }

    }

    fun setNotes(contacts : List<Contacts>) {
        allContacts = contacts
        notifyDataSetChanged()
    }

    inner class ContactsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var userName = itemView.txt_user_name
        var userPhoneNumber = itemView.txt_phone_number
    }
}