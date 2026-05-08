package com.admin.eventmanagementapp.ui.screens.event

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.admin.eventmanagementapp.R
import com.admin.eventmanagementapp.data.EventViewModel

@Composable
fun CreateEventScreen(
    viewModel: EventViewModel,
    navController: NavController
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    var success by remember { mutableStateOf("") }

    val isValid = title.isNotBlank() && description.isNotBlank()

    Box(modifier = Modifier.fillMaxSize()) {

        // 🖼 BACKGROUND IMAGE
        Image(
            painter = painterResource(id = R.drawable.create_event),
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
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )

                    Text("Create Event")

                    Spacer(Modifier.height(20.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Event Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Event Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    Spacer(Modifier.height(10.dp))

                    if (error.isNotEmpty()) {
                        Text(error, color = Color.Red)
                    }

                    if (success.isNotEmpty()) {
                        Text(success, color = Color.Green)
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {

                            if (!isValid) {
                                error = "Please fill all fields"
                                success = ""
                                return@Button
                            }

                            isLoading = true
                            error = ""
                            success = ""

                            // 📤 SAVE TO FIREBASE
                            viewModel.addEvent(title, description)

                            isLoading = false
                            success = "Event saved successfully ✔"

                            title = ""
                            description = ""

                            // Navigate back after creating
                            navController.popBackStack()
                        },
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Save Event")
                        }
                    }
                }
            }
        }
    }
}
