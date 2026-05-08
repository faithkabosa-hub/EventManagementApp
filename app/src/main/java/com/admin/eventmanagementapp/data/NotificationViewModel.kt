package com.admin.eventmanagementapp.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.admin.eventmanagementapp.models.Notification
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("notifications")

    var notifications = mutableStateListOf<Notification>()
        private set

    private var listener: ValueEventListener? = null

    init {
        loadNotifications()
    }

    private fun loadNotifications() {

        listener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                notifications.clear()

                for (data in snapshot.children) {

                    val notif = data.getValue(Notification::class.java)

                    if (notif != null) {
                        notifications.add(
                            notif.copy(id = data.key ?: "")
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // optional: log error
            }
        }

        db.addValueEventListener(listener!!)
    }

    override fun onCleared() {
        super.onCleared()
        listener?.let { db.removeEventListener(it) }
    }
}
