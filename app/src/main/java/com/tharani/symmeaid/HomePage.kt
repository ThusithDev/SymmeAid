package com.tharani.symmeaid

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tharani.symmeaid.viewModel.HomeViewModel
import com.tharani.symmeaid.viewModel.ProgressViewModel

@Composable
fun HomePage(navController: NavHostController, progressViewModel: ProgressViewModel, homeViewModel: HomeViewModel) {

    val context = LocalContext.current
    val selectedExercise by homeViewModel.selectedExercise.observeAsState()
    // If no exercise has been selected yet, show a placeholder or a default image
    val currentExercise = selectedExercise ?: Exercise("pikaso_image", "ArticleOne")

    val imageMap = mapOf(
        "exercise_one" to R.drawable.exercise_one,
        "exercise_two" to R.drawable.exercise_two,
        "exercise_three" to R.drawable.exercise_three,
        "exercise_four" to R.drawable.exercise_four
    )

    val imageResId = imageMap[currentExercise.image] ?: R.drawable.pikaso_image

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
                    val context = LocalContext.current

                    // Load progress when the screen starts
                    LaunchedEffect(Unit) {
                        progressViewModel.loadProgress { savedProgress ->
                            savedProgress?.let {
                                checkedStates.clear()
                                checkedStates.addAll(it)
                            }
                        }
                    }

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
                                        .background(
                                            if (isChecked) Color.Black else Color.Transparent,
                                            shape = RoundedCornerShape(15.dp)  // Ensure rounded corners when marked
                                        )
                                        .border(1.dp, Color.Black, RoundedCornerShape(15.dp))
                                        .clickable {
                                            if (index == 6) {
                                                // Reset all boxes when the 7th box is clicked
                                                for (i in checkedStates.indices) {
                                                    checkedStates[i] = false
                                                }
                                            } else {
                                                // Toggle the current box state
                                                checkedStates[index] = !isChecked
                                            }
                                            progressViewModel.saveProgress(checkedStates.toList()) { success ->
                                                if (!success) {
                                                    Toast.makeText(context, "Failed to save progress", Toast.LENGTH_SHORT).show()
                                                }
                                            }
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
                    text = "Recommended exercise for you",
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
                        .height(250.dp)
                        .background(Color.Black, RoundedCornerShape(25.dp))
                        .clickable {
                            navController.navigate(currentExercise.articleRoute)
                        }
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
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

/**
 *              Text(
 *                     text = "Boost your results",
 *                     color = Color.White,
 *                     fontSize = 18.sp,
 *                     fontWeight = FontWeight.Bold,
 *                     modifier = Modifier.padding(top = 30.dp, start = 24.dp)
 *                 )
 */