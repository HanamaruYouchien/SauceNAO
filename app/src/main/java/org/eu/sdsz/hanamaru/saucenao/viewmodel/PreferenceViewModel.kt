package org.eu.sdsz.hanamaru.saucenao.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import org.eu.sdsz.hanamaru.saucenao.R

class PreferenceViewModel(private val app: Application): AndroidViewModel(app) {
    private val PREF_NAME = app.getString(R.string.pref_name)
    private val PREF_FIELD_APIKEY = app.getString(R.string.pref_field_apiKey)

    private val pref = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // set apiKey would also save it to shared preferences
    var apiKey = pref.getString(PREF_FIELD_APIKEY, "") ?: ""
        set(value) {
            field = value
            setPrefApiKey(value)
        }

    private fun setPrefApiKey(value: String) {
        val editor = pref.edit()
        editor.putString(PREF_FIELD_APIKEY, value)
        editor.apply()
    }
}