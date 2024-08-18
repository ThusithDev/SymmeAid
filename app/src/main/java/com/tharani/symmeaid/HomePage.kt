package com.tharani.symmeaid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tharani.symmeaid.viewModel.HomeViewModel

@Composable
fun HomePage(navController: NavHostController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_one_t))
    val viewModel: HomeViewModel = viewModel()
    val userProfile by viewModel.userProfile.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = true)
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
            title = "Capture",
            selectedIcon = R.drawable.capture,
            unselectedIcon = R.drawable.capture,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Exercises",
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
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

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
                Text(
                    text = "Welcome to \n SymmeAid",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp, start = 100.dp)
                )

                Text(
                    text = "Watch your progress",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp, start = 24.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, end = 24.dp, start = 24.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(20.dp))
                        .height(100.dp)
                        .background(Color.White, RoundedCornerShape(20.dp))
                ) {
                    val checkedStates = remember { mutableStateListOf<Boolean>(false, false, false, false, false, false, false) }

                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(text = "Progress of the Week", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        HorizontalDivider(thickness = 1.dp, color = Color.Black)

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, top = 10.dp, end = 10.dp)
                        ) {
                            repeat(7) { index ->
                                val isChecked = checkedStates[index]
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(if (isChecked) Color.Black else Color.Transparent)
                                        .border(1.dp, Color.Black, RoundedCornerShape(15.dp))
                                        .clickable {
                                            checkedStates[index] = !isChecked
                                        }
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        textAlign = TextAlign.Center,
                                        color = if (isChecked) Color.White else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Text(
                    text = "Keep your step for today",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp, start = 24.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, end = 24.dp, start = 24.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(25.dp))
                        .height(150.dp)
                        .background(Color.Black, RoundedCornerShape(25.dp))
                ) {

                }

                Text(
                    text = "Boost your results",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp, start = 24.dp)
                )

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
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    backgroundColor: Color = Color(0xFF32A8A4),
    height: Dp = 46.dp
) {
    Surface(
        modifier = Modifier
            .background(Color.Cyan)
            .padding(bottom = 10.dp)
            .height(height)
            .clip(RoundedCornerShape(16.dp)), // Set desired corner radius
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        if (item.badgeCount != null && item.badgeCount > 0) {
                            BadgedBox(badge = { Badge { Text("${item.badgeCount}") } }) {
                                Icon(
                                    painterResource(id = if (selectedIndex == index) item.selectedIcon else item.unselectedIcon),
                                    contentDescription = item.title,
                                    tint = if (selectedIndex == index) Color.White else Color.Black,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        } else {
                            Icon(
                                painterResource(id = if (selectedIndex == index) item.selectedIcon else item.unselectedIcon),
                                contentDescription = item.title,
                                tint = if (selectedIndex == index) Color.White else Color.Black,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    },
                    selected = selectedIndex == index,
                    onClick = { onItemSelected(index) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Black,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.Black,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val parameterizedRoute: String? = null
)

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
