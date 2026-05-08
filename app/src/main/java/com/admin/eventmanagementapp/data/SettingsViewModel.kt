package com.admin.eventmanagementapp.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingsViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("users")

    var darkMode = mutableStateOf(false)
    var userId = mutableStateOf("")

    init {
        loadSettings()
    }

    private fun loadSettings() {

        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (user in snapshot.children) {

                    userId.value = user.key ?: ""

                    val mode = user.child("darkMode").getValue(Boolean::class.java)
                    darkMode.value = mode ?: false

                    break
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun updateDarkMode(value: Boolean) {
        darkMode.value = value

        if (userId.value.isNotEmpty()) {
            db.child(userId.value)
                .child("darkMode")
                .setValue(value)
        }
    }
}
