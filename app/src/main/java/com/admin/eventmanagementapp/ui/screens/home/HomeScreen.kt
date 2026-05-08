package com.admin.eventmanagementapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.admin.eventmanagementapp.R
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.admin.eventmanagementapp.data.EventViewModel
import com.admin.eventmanagementapp.navigation.Routes

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: EventViewModel = viewModel()
) {

    val events = viewModel.events

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🖼️ HOME IMAGE
            Image(
                painter = painterResource(id = R.drawable.home_banner),
                contentDescription = "Home Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Event Manager",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFFFF5722)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(Routes.CREATE_EVENT)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Event")
            }

            // ✅ NAVIGATION BUTTONS ADDED
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(Routes.EVENT_LIST)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Events")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(Routes.NOTIFICATIONS)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Notifications")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate(Routes.PROFILE)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Profile")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (events.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No events yet")
                }

            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(events) { event ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Column(modifier = Modifier.padding(16.dp)) {

                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(event.date)
                                Text(event.description)

                                Spacer(modifier = Modifier.height(10.dp))

                                Row {

                                    Button(
                                        onClick = {
                                            navController.navigate(Routes.EVENT_DETAILS + "/${event.id}")
                                        }
                                    ) {
                                        Text("View")
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            viewModel.deleteEvent(event.id)
                                        }
                                    ) {
                                        Text("Delete")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}