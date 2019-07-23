package io.github.harzz.kontacts.utils

import android.content.Context
import io.github.harzz.kontacts.R

class SharedPrefManager (val context: Context){

    var isLoggedIn: Boolean
        get() = SharedPreference.instance.getBooleanValue(context, context.getString(
            R.string.preference_is_logged_in
        ))!!
        set(isLoggedIn) = SharedPreference.instance.setValue(context, context.getString(
            R.string.preference_is_logged_in
        ), isLoggedIn)

    var currentUserInfo: String?
        get() = SharedPreference.instance.getStringValue(context, context.getString(
            R.string.preference_user_info
        ))
        set(userInfo) = SharedPreference.instance.setValue(context, context.getString(
            R.string.preference_user_info
        ), userInfo)

    fun clearPref() = SharedPreference.instance.clearPreferences(context)

}