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
fun ArticleTwo(navController: NavHostController) {
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
                Text(text = "The Role of Yoga in Inner Healing", modifier = Modifier.padding(start = 12.dp), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Yoga, an ancient practice that combines physical postures, breath control, and meditation, offers\n" +
                            "numerous benefits for inner healing. Regular yoga practice can reduce stress, enhance flexibility, and\n" +
                            "improve overall physical health. The practice emphasizes the connection between body, mind, and spirit,\n" +
                            "which is essential for achieving holistic well-being.\n" +
                            "Yoga helps balance the autonomic nervous system, reducing the effects of chronic stress and promoting\n" +
                            "relaxation. The physical postures (asanas) strengthen muscles, improve circulation, and support the\n" +
                            "detoxification process. Breathwork (pranayama) enhances oxygen flow to the brain and body, while\n" +
                            "meditation fosters mental clarity and emotional stability.\n" +
                            "Incorporating yoga into your routine can support mental health by reducing anxiety and depression, and\n" +
                            "physical health by improving posture and reducing pain. For those seeking natural ways to support inner\n" +
                            "healing, yoga offers a comprehensive approach that nurtures both body and mind.", modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

    }
}

