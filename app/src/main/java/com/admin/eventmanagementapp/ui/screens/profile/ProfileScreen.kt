package com.admin.eventmanagementapp.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.admin.eventmanagementapp.data.ProfileViewModel
import com.admin.eventmanagementapp.ui.screens.auth.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.admin.eventmanagementapp.R
import com.admin.eventmanagementapp.navigation.Routes

@Composable
fun ProfileScreen(navController: NavController) {

    val viewModel: ProfileViewModel = viewModel()
    val userState = viewModel.user.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // 🖼️ BACKGROUND ADDED (UNCHANGED)
        Image(
            painter = painterResource(id = R.drawable.appbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            // ⭐ ONLY ADDITION (IMAGE) — UNCHANGED
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFFFF5722)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Name: ${userState.fullname}")
                    Text("Email: ${userState.email}")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722)
                )
            ) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = {
                    navController.navigate(Routes.SETTINGS)
                }
            ) {
                Text("Go to Settings")
            }
        }
    }
}