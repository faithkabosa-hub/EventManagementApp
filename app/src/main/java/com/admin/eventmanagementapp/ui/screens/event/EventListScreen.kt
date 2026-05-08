package com.admin.eventmanagementapp.ui.screens.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.admin.eventmanagementapp.R
import com.admin.eventmanagementapp.data.EventViewModel
import com.admin.eventmanagementapp.navigation.Routes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventViewModel
) {
    val events = viewModel.events
    var search by remember { mutableStateOf("") }

    val filteredEvents = remember(search, events) {
        events.filter {
            it.title.contains(search, ignoreCase = true)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🟣 BACKGROUND
        Image(
            painter = painterResource(id = R.drawable.appbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            // 🖼 HEADER IMAGE
            Image(
                painter = painterResource(id = R.drawable.event_list),
                contentDescription = "Event List",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search events") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredEvents.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No events found")
                }
            } else {
                LazyColumn {
                    items(filteredEvents) { event ->
                        EventCard(
                            event = event,
                            onClick = {
                                navController.navigate("${Routes.EVENT_DETAILS}/${event.id}")
                            },
                            onEdit = {
                                navController.navigate("${Routes.EDIT_EVENT}/${event.id}")
                            },
                            onDelete = {
                                viewModel.deleteEvent(event.id)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}