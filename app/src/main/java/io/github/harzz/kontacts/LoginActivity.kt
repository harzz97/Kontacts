package io.github.harzz.kontacts

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




    }

    fun onClickLogin(view : View){
        if(isValidLogin()){
            Toast.makeText(this, "Valid login!", Toast.LENGTH_SHORT).show()

            /*val bundle = Bundle()
            bundle.putString("OwnerName", etEmail?.text.toString())
            val frag = AddContactBottomSheetFragment()
            frag.arguments = bundle*/

            val homePageIntent = Intent(this, MainActivity::class.java)
            startActivity(homePageIntent)

        }else{
            //show error msg via toast or textbox error msg
        }
    }

    private fun isValidLogin() : Boolean{
        //verify email
        if(!isValidEmail(editText_login_user_email.toString()))  return false
        //verify the password
        if(!isValidPassword(editText_login_user_password.toString())) return false

        return true

    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun isValidPassword(password : CharSequence?) : Boolean {
        return if(password == null ){
            false
        }else{
            var strength : Int = 0
            if (password.matches(Regex(".*[a-z]*."))) strength++
            if (password.matches(Regex(".*[A-Z]*."))) strength++
            if (password.matches(Regex(".*\\d*.")))  strength++
            if(password.length >= 6 ) strength++
            Log.v("LA",strength.toString())
            return strength >= 4
        }
    }
}