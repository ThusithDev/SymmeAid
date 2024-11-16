package com.tharani.symmeaid

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.tharani.symmeaid.viewModel.UserDetailsViewModel
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.tharani.symmeaid.viewModel.RegisterViewModel

@Composable
fun Profile(
    navController: NavHostController,
    viewModel: UserDetailsViewModel,
    registerViewModel: RegisterViewModel
) {
    val profileData by viewModel.profileData

    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            viewModel.fetchUserProfile()
        } else {
            viewModel.clearProfileData()
            Log.e("User Error", "No current user")
        }
    }

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
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(3) }

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
                profileData?.let { profile ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (profile.profileImageUrl != null) {
                            Image(
                                painter = rememberAsyncImagePainter(profile.profileImageUrl),
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.White, CircleShape)
                            )
                        } else {
                            // Display a default image or an error message
                            Text("No profile image available", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Name: ${profile.name}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Age: ${profile.age}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Gender: ${profile.gender}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Feedback button
                        Button(onClick = {
                            navController.navigate("Feedback") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 100.dp).width(260.dp).height(55.dp)
                        ) {
                            Text(text = "Feedback")
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Logout button
                        Button(onClick = {
                            registerViewModel.logout(viewModel) {
                                // Navigate to the LoginScreen or perform other actions after logout
                                navController.navigate("LoginScreen") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp).width(260.dp).height(55.dp)
                        ) {
                            Text(text = "Log out")
                        }
                    }
                } ?: run {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }
}
