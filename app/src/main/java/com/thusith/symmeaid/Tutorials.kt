package com.thusith.symmeaid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Tutorials( navController: NavHostController) {

    val gradient = Brush.linearGradient(
        0.0f to Color.Black,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    val items = listOf(
        BottomNavigationItem(
            title = "HomePage", //HomePage/$profileImageUrl
            selectedIcon = R.drawable.aim,
            unselectedIcon = R.drawable.aim,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "CaptureTwo",
            selectedIcon = R.drawable.capture,
            unselectedIcon = R.drawable.capture,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Tutorials",
            selectedIcon = R.drawable.book,
            unselectedIcon = R.drawable.book,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = R.drawable.person,
            unselectedIcon = R.drawable.person,
            hasNews = false
        )
    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(2) }

    Scaffold(
        modifier = Modifier.background(Color.Black),
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedIndex = selectedItemIndex,
                onItemSelected = { index ->
                    selectedItemIndex = index
                    // Handle navigation or other logic here
                    navController.navigate(items[index].title)
                },
                height = 40.dp  // Custom height
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(gradient),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Spacer(modifier = Modifier.height(14.dp))
                RecommendedActivityCard(
                    title = "Calming Breathing",
                    description = "This breathing exercise will help you stay energized...",
                    imageResId = R.drawable.calm_breathing, // Replace with your image resource
                    onClick = { navController.navigate("ArticleOne") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                RecommendedActivityCard(
                    title = "Yoga for Inner Healing",
                    description = "This yoga will help reduce your stress & improve fitness...",
                    imageResId = R.drawable.yoga_image, // Replace with your image resource
                    onClick = { navController.navigate("ArticleTwo") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                RecommendedActivityCard(
                    title = "Nature Walks",
                    description = "This exercise will help you lower stress levels...",
                    imageResId = R.drawable.nature, // Replace with your image resource
                    onClick = { navController.navigate("ArticleThree") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                RecommendedActivityCard(
                    title = "Nutritional Strategies",
                    description = "This strategies will support you natural healing...",
                    imageResId = R.drawable.nutrition, // Replace with your image resource
                    onClick = { navController.navigate("ArticleFour") }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RecommendedActivityCard(title: String, description: String, imageResId: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 24.dp, end = 24.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .width(60.dp)
                    .height(66.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
