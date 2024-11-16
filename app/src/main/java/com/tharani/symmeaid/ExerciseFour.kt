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
fun ExerciseFour(navController: NavHostController) {
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
                    text = "Slowly rotate your head in a circular motion to stretch the neck muscles. Perform in both directions for a balanced stretch.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Press your tongue against the roof of your mouth as hard as you can while keeping your mouth closed. Hold for 10 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "With your lips closed, move your tongue in circular motions along the inside of your mouth to engage the muscles around the mouth and cheeks.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Try to smile while using your fingers to push down on the corners of your lips to create resistance.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Perform all the above each for 20 seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Do two sets of all above exercises.", modifier = Modifier.padding(start = 12.dp)
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