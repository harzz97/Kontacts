package io.github.harzz.kontacts

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.harzz.kontacts.adapter.ContactsAdapter
import io.github.harzz.kontacts.repository.entity.Contacts
import io.github.harzz.kontacts.utils.SharedPrefManager
import io.github.harzz.kontacts.viewmodel.ContactsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var adapter : ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val contactsBottomSheet = ContactsBottomSheet.newInstance()

        val recyclerView : CustomRecyclerView = contacts_recycler_view as CustomRecyclerView
        recyclerView.setEmptyView(txt_empty_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ContactsAdapter(){
            //TODO: Create a serializable to share between sheet and recyclerview
            val editSheet = ContactsBottomSheet.newInstance()
            val bundle = Bundle()
            bundle.putString("id",it.id.toString())
            bundle.putString("owner",it.owner)
            bundle.putString("user_name",it.user_name)
            bundle.putString("user_phone_number",it.user_phone_number)
            editSheet.arguments = bundle
            editSheet.show(supportFragmentManager,"edit_bottom_sheet")

        }

        recyclerView.adapter = adapter
        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        contactsViewModel.getAllContacts().observe(this,
            Observer<List<Contacts>> {
                //observe for changes and update adapter
                    t -> adapter.setContacts(t!!)
            })

        //when user press fab display bottomsheet to create new contact
        fab.setOnClickListener { view ->
            contactsBottomSheet.show(
                supportFragmentManager,
                "add_bottom_sheet"
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                SharedPrefManager(this).clearPref()
                navigateToHome()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToHome(){
        val loginPageIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginPageIntent)
        finish()
    }

}
