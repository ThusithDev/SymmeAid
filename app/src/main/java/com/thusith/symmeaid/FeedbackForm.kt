package com.thusith.symmeaid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Layouts
import androidx.compose.material3.* // Material 3 components
import androidx.compose.runtime.* // State handling
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FeedbackForm(navController: NavHostController) {
    var feedbackText by remember { mutableStateOf(TextFieldValue("")) }
    val gradient = Brush.linearGradient(
        0.0f to Color.Black,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).background(gradient),
        verticalArrangement = Arrangement.Center
    ) {
        // Feedback input field
        TextField(
            value = feedbackText,
            onValueChange = { newText -> feedbackText = newText },
            label = { Text("Enter your feedback") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)  // Increase the height here
                .padding(bottom = 16.dp),
            singleLine = false,  // Allows multiline text input
        )

        // Send Button
        Button(
            onClick = {
                // Handle sending feedback logic here
                println("Feedback sent: ${feedbackText.text}")
                navController.navigate("Profile")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
        }
    }
}