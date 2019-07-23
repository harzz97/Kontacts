package io.github.harzz.kontacts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.harzz.kontacts.repository.entity.Users
import io.github.harzz.kontacts.utils.SharedPrefManager
import io.github.harzz.kontacts.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var alertDialog: AlertDialog
    var isUserSuccess : Boolean = false
    private  lateinit var allUsers : LiveData<List<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //if user not logged in
        if(SharedPrefManager(this).isLoggedIn){
            moveToHomeActivity()
        }else{
            loginViewModel  = ViewModelProviders.of(this).get(LoginViewModel::class.java)
            Thread(Runnable {
                allUsers = loginViewModel.getAllUsers()!!
            }).start()
        }
    }

    //when signup text is pressed
    fun displaySignUpDialog(view: View){
        val openDialog = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_signup,null,false)
        openDialog.setView(dialogView)
        alertDialog = openDialog.create()
        alertDialog.show()
    }

    //when signup button is pressed
    fun onClickSignup(view:View){
        if(isValidSignup()){
               createNewUser()
        }else{
            Toast.makeText(this,"Pls enter valid details",Toast.LENGTH_LONG).show()
        }
    }

    //when login button is pressed
    fun onClickLogin(view : View){
        if(isValidLoginDetails()){
            val currUserEmail = editText_login_user_email.text.toString()
            val currentUserPassword = editText_login_user_password.text.toString().sha256()
            val user = Users("",currentUserPassword,currUserEmail)

            loginViewModel.getUserDetails(user)?.observe(this,
                Observer {
                if(it != null){

                    if(it.email == currUserEmail){
                        if(it.userPassword == currentUserPassword){
                            storeSharedPref(currUserEmail)
                            moveToHomeActivity()
                        }else{
                            editText_login_user_password.error = "Enter valid password"
                        }
                    }
                }else{
                    Toast.makeText(this,"Pls Sign up First",Toast.LENGTH_LONG).show()
                }
            })
        }else{
            //show error msg via toast or textbox error msg
            Toast.makeText(this,"Invalid Login Details",Toast.LENGTH_LONG).show()
        }
    }

    private fun isValidSignup():Boolean{
        val email = alertDialog.findViewById<EditText>(R.id.editText_email)!!
        val password = alertDialog.findViewById<EditText>(R.id.editText_password)!!
        val rePassword = alertDialog.findViewById<EditText>(R.id.editText_rePassword)!!

        //verify email
        if(!isValidEmail(email.text.toString())){
            email.error = "Pls enter valid email"
            return false
        }
        //verify the password
        if(!isValidPassword(password.text.toString())){
            password.error = "Pls enter valid password"
            return false
        }

        if(!isValidPassword(rePassword.text.toString())) {
            rePassword.error = "Pls enter valid password"
            return false
        }

        if(!password.text.toString().equals(rePassword.text.toString()))  {
            rePassword.setText("")
            Toast.makeText(this,"Passwords don't match",Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun isValidLoginDetails() : Boolean{
        //verify email
        if(!isValidEmail(editText_login_user_email.text.toString())){
            editText_login_user_email.error = "Pls enter valid email"
            return false
        }
        //verify the password
        if(!isValidPassword(editText_login_user_password.text.toString())){
            Toast.makeText(this,"Pls enter valid password",Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
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
            return strength >= 4
        }
    }


    private fun createNewUser() {
        val email = alertDialog.findViewById<EditText>(R.id.editText_email)
        val password = alertDialog.findViewById<EditText>(R.id.editText_password)
        val userName = alertDialog.findViewById<EditText>(R.id.editText_userName)
        val user = Users(userName?.text.toString(),password?.text.toString().sha256(),email?.text.toString())

        allUsers.observe(this, Observer<List<String>> {
            if(it.contains(email!!.text.toString())){
                if(!isUserSuccess)
                    Toast.makeText(this,"User Account already exists Pls Login",Toast.LENGTH_LONG).show()
            }else{
                isUserSuccess = true
                loginViewModel.createNewUser(user)
                alertDialog.dismiss()
                storeSharedPref(alertDialog.findViewById<EditText>(R.id.editText_email).toString())
                moveToHomeActivity()
            }
        })

    }

    //an extension program to generate sha256 for strings
    private fun String.sha256():String{
        val userPasswordBytes = this.toByteArray()
        MessageDigest.getInstance("SHA-256")
        return userPasswordBytes.fold("",{str,it->str+"%02x".format(it)})
    }
    
    private fun moveToHomeActivity(){
        val homePageIntent = Intent(this, MainActivity::class.java)
        startActivity(homePageIntent)
        finish()
    }

    private fun storeSharedPref(userEmail : String){
        SharedPrefManager(this).isLoggedIn = true
        SharedPrefManager(this).currentUserInfo = userEmail
    }
}