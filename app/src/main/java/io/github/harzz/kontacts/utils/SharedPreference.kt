package io.github.harzz.kontacts

import android.content.Context
import android.content.SharedPreferences

class SharedPreference (){

    companion object{
        private var preference : SharedPreferences? = null
        private var customPreference : SharedPreference? = null
        val instance: SharedPreference
            get() {
                if (customPreference != null) {
                    return customPreference as SharedPreference
                } else {
                    customPreference = SharedPreference()
                    return customPreference as SharedPreference
                }
            }
    }

    private var preference: SharedPreferences? = null

    //singelton instance
    private fun getInstance(context: Context): SharedPreferences? {
        if (preference != null) {
            return preference
        } else {
            preference = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            return preference
        }
    }

    //store the given key value pair in SharedPreference
    //@param value is string
    fun setValue(context: Context, key: String, value: String?) {
        getInstance(context)
        val editor = preference!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setValue(context: Context,key: String,value: Boolean?){
        getInstance(context)
        val editor = preference!!.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

    //return the boolean value for given key
    fun getBooleanValue(context: Context, key: String): Boolean? {
        return getInstance(context)!!.getBoolean(key, false)
    }

    //return the string value for the given key
    fun getStringValue(context: Context, key: String): String? {
        return getInstance(context)!!.getString(key, null)
    }

}