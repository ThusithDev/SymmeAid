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
fun ExerciseFive(navController: NavHostController) {
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
                    text = "Smile as widely as possible while keeping their lips closed.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Then use your fingers to gently lift their cheeks, holding the position for 10 seconds before relaxing.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Users open their mouths wide while keeping their lips closed and hold the position for a few seconds.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Using your hands, provide gentle resistance as you move their jaw left and right, helping to strengthen the weaker side of the jaw.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Place the tongue flat against the roof of the mouth, with lips closed and teeth gently touching.", modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Press your lips together tightly, hold for a few seconds, and then relax.", modifier = Modifier.padding(start = 12.dp)
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