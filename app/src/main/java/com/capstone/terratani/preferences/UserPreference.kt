package com.capstone.terratani.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

//val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")
class UserPreference(private val dataStore: DataStore<Preferences>) {

}