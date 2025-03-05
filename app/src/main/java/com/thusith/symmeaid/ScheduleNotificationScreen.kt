package com.thusith.symmeaid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.thusith.symmeaid.viewModel.HomeViewModel
import com.thusith.symmeaid.viewModel.NotificationViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScheduleNotificationScreen(navController: NavHostController, notificationViewModel: NotificationViewModel, homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    val viewModel: NotificationViewModel = viewModel()
    var selectedTime by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // TimePickerDialog needs to be created only when required
    DisposableEffect(Unit) {
        onDispose {
            // Cleanup or dismiss actions here
            showDialog = false // Ensure dialog is hidden when screen is removed
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the selected time at the top
        Text(text = "Selected Time: $selectedTime")

        Spacer(modifier = Modifier.height(12.dp))

        // Button to trigger time picker
        Button(onClick = { showDialog = true }) {
            Text(text = "Select Time")
        }

        // Only show TimePickerDialog when needed
        if (showDialog) {
            TimePicker(onTimeSelected = { timeInMillis ->
                selectedTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timeInMillis))

                // Schedule the notification with the selected time
                viewModel.scheduleNotification(context, timeInMillis, "Reminder", "It's time for your exercises!")

                // Dismiss the dialog after selection
                showDialog = false
            })
        }

        Spacer(modifier = Modifier.height(32.dp))

        // "Done" button with a reduced size
        Button(
            onClick = {
                homeViewModel.setRandomExercise()
                navController.navigate("HomePage")
            },
            modifier = Modifier
                .size(150.dp, 50.dp) // Adjust the size of the button
                .padding(8.dp)
        ) {
            Text(text = "Done")
        }
    }
}