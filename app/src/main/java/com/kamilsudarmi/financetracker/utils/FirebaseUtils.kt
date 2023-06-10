package com.kamilsudarmi.financetracker.utils

import com.google.firebase.database.FirebaseDatabase

object FirebaseUtils {
    private var database: FirebaseDatabase? = null

    fun getDatabase(): FirebaseDatabase {
        if (database == null) {
            database = FirebaseDatabase.getInstance()
            database!!.setPersistenceEnabled(true) // Enable offline persistence
        }
        return database!!
    }
}
