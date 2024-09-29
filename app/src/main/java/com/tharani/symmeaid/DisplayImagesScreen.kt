package com.tharani.symmeaid

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.type.Date
import com.tharani.symmeaid.viewModel.FaceCaptureViewModel
import java.net.URLDecoder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@Composable
fun DisplayImagesScreen(navController: NavHostController, faceCaptureViewModel: FaceCaptureViewModel) {
    var oldestImageUrl by remember { mutableStateOf<String?>(null) }
    var mostRecentImageUrl by remember { mutableStateOf<String?>(null) }
    var statusText by remember { mutableStateOf<String>("") }
    val context = LocalContext.current

    // Fetch the oldest image URL
    // Fetch the oldest image URL
    LaunchedEffect(Unit) {
        faceCaptureViewModel.getOldestImageUrl { url ->
            oldestImageUrl = url
            url?.let {
                val isOlderThan30Days = checkIfOlderThan30Days(it)
                statusText = if (isOlderThan30Days) "Asymmetry improved" else "No change in Asymmetry"
            }
        }
    }

    // Fetch the most recent image URL
    LaunchedEffect(Unit) {
        faceCaptureViewModel.getMostRecentImageUrl { url ->
            mostRecentImageUrl = url
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the oldest image
        oldestImageUrl?.let { url ->
            Text(text = "Photo taken earlier", style = MaterialTheme.typography.bodyMedium)
            AsyncImage(
                model = url,
                contentDescription = "Oldest Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
            )
        } ?: Text(text = "Loading oldest image...", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(32.dp))

        // Display the most recent image
        mostRecentImageUrl?.let { url ->
            Text(text = "Photo taken Now", style = MaterialTheme.typography.bodyMedium)
            AsyncImage(
                model = url,
                contentDescription = "Most Recent Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
            )
        } ?: Text(text = "Loading most recent image...", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(32.dp))
        Text(text = statusText, color = Color.Blue, fontSize = 16.sp, fontWeight = FontWeight.Bold,)
    }
}

// Helper function to check if the oldest image is older than 30 days
private fun checkIfOlderThan30Days(imageUrl: String): Boolean {
    // Decode the URL to properly extract the timestamp
    val decodedUrl = URLDecoder.decode(imageUrl, "UTF-8")
    val filename = decodedUrl.substringAfterLast("/")
    val timestampString = filename.substringBefore(".jpg")

    Log.d("CheckDate", "Decoded URL: $decodedUrl, Extracted filename: $filename, Timestamp string: $timestampString")

    return try {
        // Convert the timestamp string to a Long (milliseconds since epoch)
        val imageTimestamp = timestampString.toLongOrNull()

        if (imageTimestamp == null) {
            Log.e("CheckDate", "Invalid timestamp format in filename: $timestampString")
            return false
        }

        Log.d("CheckDate", "Parsed image timestamp: $imageTimestamp")

        // Convert the timestamp to LocalDateTime
        val imageDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(imageTimestamp), ZoneId.systemDefault())
        val currentDateTime = LocalDateTime.now()

        Log.d("CheckDate", "Image date: $imageDateTime, Current date: $currentDateTime")

        // Calculate the difference between the dates in days
        val daysDifference = ChronoUnit.DAYS.between(imageDateTime, currentDateTime)

        // Log to verify days difference
        Log.d("CheckDate", "Days difference: $daysDifference days")

        // Return true if the image is older than 30 day for testing, or change to 30 days for actual use case
        daysDifference > 30 // Change to > 30 for actual condition
    } catch (e: Exception) {
        Log.e("CheckDate", "Error parsing date from filename", e)
        false
    }
}