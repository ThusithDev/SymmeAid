package com.tharani.symmeaid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun ExerciseOne(navController: NavHostController) {
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
                    text = "Raise your eyebrows as high as possible. Hold for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Then use your fingers to smooth out any wrinkles on your forehead. Hold for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Fill both cheeks with air and hold it for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Alternate the air between each cheek. Do this for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Push the tip of your tongue against the bottom front teeth, while pulling your chin inward. Do this for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Slowly rotate your head in a circular motion to stretch the neck muscles. Perform in both directions for a balanced stretch. Do this for 2 sides for 20 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Do two sets of all above exercises.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Massage all over the face and neck gently using your tip of fingers of hand to relax.\n", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}