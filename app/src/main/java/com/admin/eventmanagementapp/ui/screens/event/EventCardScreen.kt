package com.admin.eventmanagementapp.ui.screens.event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.admin.eventmanagementapp.models.Event

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(event.title)
            Text(event.location)
            Text(event.date)


            Spacer(modifier = Modifier.height(12.dp))

            Row {

                Button(onClick = onClick) {
                    Text("View")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = onEdit) {
                    Text("Edit")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}











