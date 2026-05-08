package com.admin.eventmanagementapp.data

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                onError(it.message ?: "Registration failed")
            }
    }


}



private val auth = FirebaseAuth.getInstance()

fun login(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    if (email.isBlank()||password.isBlank()){
        onError("please fill all fields")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener {
            onError(it.message ?: "Login failed")
        }
}



fun logout() {
    auth.signOut()
}


