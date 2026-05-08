package com.admin.eventmanagementapp.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.admin.eventmanagementapp.models.Event
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class EventViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("events")

    private val _events = mutableStateListOf<Event>()
    val events: List<Event> = _events

    private var listener: ValueEventListener? = null

    init {
        loadEvents()
    }

    // 📥 READ EVENTS
    private fun loadEvents() {

        listener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                _events.clear()

                for (data in snapshot.children) {

                    val event = data.getValue(Event::class.java)

                    event?.let {
                        _events.add(it.copy(id = data.key ?: ""))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // optional: handle error
            }
        }

        db.addValueEventListener(listener!!)
    }

    // 📤 CREATE EVENT
    fun addEvent(title: String, description: String) {

        val id = db.push().key ?: return

        val event = Event(
            id = id,
            title = title,
            description = description
        )

        db.child(id).setValue(event)
    }

    // ✏ UPDATE EVENT
    fun updateEvent(event: Event) {

        if (event.id.isBlank()) return

        db.child(event.id).setValue(event)
    }

    // ❌ DELETE EVENT
    fun deleteEvent(id: String) {

        if (id.isBlank()) return

        db.child(id).removeValue()
    }

    override fun onCleared() {
        super.onCleared()

        listener?.let {
            db.removeEventListener(it)
        }
    }
}
