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
fun ArticleOne(navController: NavHostController) {
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
                Text(text = "The Power of Mindfulness", modifier = Modifier.padding(start = 12.dp), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Mindfulness, the practice of being fully present in the moment, has profound benefits for both mental\n" +
                            "and physical health. When we focus on the present, we reduce stress and anxiety, which can trigger " +
                            "inflammation and disrupt the body's natural healing processes. Studies show that mindfulness\n" +
                            "meditation lowers cortisol levels, a stress hormone that can impair immune function and overall health.\n" +
                            "By regularly practicing mindfulness, individuals report improved emotional resilience, reduced pain\n" +
                            "perception, and enhanced immune response.\n Engaging in mindfulness involves techniques such as focused breathing and guided meditations. These\n" +
                            "practices help calm the nervous system, improve sleep quality, and promote emotional balance. Over\n" +
                            "time, mindfulness can enhance self-awareness, allowing individuals to better manage stress and make\n" +
                            "healthier lifestyle choices. For those seeking natural and inner healing, incorporating mindfulness into\n" +
                            "daily routines can be a powerful tool. It fosters a deeper connection between the mind and body,\n" +
                            "supporting the body's innate ability to heal itself", modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

    }
}