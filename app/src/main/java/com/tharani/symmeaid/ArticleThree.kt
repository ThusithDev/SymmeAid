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
fun ArticleThree(navController: NavHostController) {
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
                Text(text = "The Power of Nature Walks", modifier = Modifier.padding(start = 12.dp), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Spending time in nature has been shown to have significant healing effects on both physical and mental\n" +
                            "health. Nature walks, in particular, can lower stress levels, boost mood, and improve overall well-being.\n" +
                            "The act of walking among trees, listening to birdsong, and breathing fresh air provides a natural respite\n" +
                            "from the hustle and bustle of daily life.\n" +
                            "Research indicates that time spent in natural environments can lower blood pressure, reduce heart rate,\n" +
                            "and decrease levels of the stress hormone cortisol. Nature walks also promote physical activity, which is\n" +
                            "beneficial for cardiovascular health and weight management. Additionally, exposure to nature has been\n" +
                            "linked to improved concentration and cognitive function.\n" +
                            "To reap the benefits of nature walks, aim to spend at least 30 minutes a few times a week in a natural\n" +
                            "setting. Whether it’s a park, forest, or beach, immersing yourself in nature can rejuvenate the mind and\n" +
                            "body, supporting overall health and healing.", modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

    }
}

