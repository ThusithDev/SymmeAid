package com.thusith.symmeaid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ExerciseThree(navController: NavHostController) {
    Scaffold() {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(text = "Recommended exercise steps", modifier = Modifier.padding(start = 14.dp), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Clench your jaw tightly for 10 seconds, then relax.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Lift your lower lip as high as you can while keeping the rest of your face relaxed. Hold the position for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Push your lower lip up towards your upper lip, engaging the muscles under your chin. Hold for a 10 seconds, then release.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Tilt your head upwards and push your lower jaw forward. Hold the stretch to tighten the muscles under the chin and along the jawline for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Place your index fingers at the corners of your mouth and try to smile widely, using your fingers to create resistance 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Repeat the above set of exercises twice.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Massage all over the face and neck gently using your tip of fingers of hand to relax.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}