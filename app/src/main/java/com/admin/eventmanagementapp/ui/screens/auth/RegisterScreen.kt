package com.admin.eventmanagementapp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.admin.eventmanagementapp.R
import com.admin.eventmanagementapp.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    navController: NavController
) {

    val auth = FirebaseAuth.getInstance()

    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val passwordsMatch = password == confirmpassword

    val isValid =
        fullname.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmpassword.isNotBlank() &&
                passwordsMatch

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // 🖼️ BACKGROUND
        Image(
            painter = painterResource(id = R.drawable.appbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(80.dp)
                    )

                    Text("Create Account")

                    Spacer(Modifier.height(20.dp))

                    OutlinedTextField(
                        value = fullname,
                        onValueChange = { fullname = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = confirmpassword,
                        onValueChange = { confirmpassword = it },
                        label = { Text("Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (!passwordsMatch && confirmpassword.isNotEmpty()) {
                        Text("Passwords do not match", color = Color.Red)
                    }

                    if (error.isNotEmpty()) {
                        Text(error, color = Color.Red)
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {

                            isLoading = true
                            error = ""

                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->

                                    if (task.isSuccessful) {

                                        val user = auth.currentUser

                                        val db = com.google.firebase.database.FirebaseDatabase.getInstance()
                                            .getReference("users")

                                        val userData = mapOf(
                                            "id" to (user?.uid ?: ""),
                                            "fullname" to fullname,
                                            "email" to email
                                        )

                                        user?.uid?.let { uid ->
                                            db.child(uid).setValue(userData)
                                        }

                                        isLoading = false

                                        // Navigate to LOGIN after registration
                                        navController.navigate(Routes.LOGIN) {
                                            popUpTo(Routes.REGISTER) { inclusive = true }
                                        }

                                    } else {

                                        isLoading = false
                                        error = task.exception?.message ?: "Registration failed"
                                    }
                                }

                        },
                        enabled = isValid && !isLoading,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Sign up")
                        }
                    }

                    TextButton(onClick = {
                        navController.navigate(Routes.LOGIN)
                    }) {
                        Text("Already have an account? Login")
                    }
                }
            }
        }
    }
}
