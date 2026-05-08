package com.admin.eventmanagementapp.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.admin.eventmanagementapp.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("users")

    var user = mutableStateOf(User())
        private set

    init {
        loadUser()
    }

    private fun loadUser() {

        db.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (data in snapshot.children) {

                    val fetchedUser = data.getValue(User::class.java)

                    if (fetchedUser != null) {
                        user.value = fetchedUser.copy(id = data.key ?: "")
                        break // using first user (same logic you had)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
