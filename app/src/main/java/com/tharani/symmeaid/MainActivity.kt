package com.tharani.symmeaid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp
import com.tharani.symmeaid.ui.theme.SymmeAidTheme
import com.tharani.symmeaid.viewModel.UserDetailsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            SymmeAidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userDetailsViewModel: UserDetailsViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "Splash"){
                        composable("Splash"){
                            SplashScreen(navController = navController)
                        }
                        composable("HomeScreen"){
                            HomeScreen(navController = navController)
                        }
                        composable("Onboarding"){
                            OnboardingScreen(navController = navController)
                        }
                        composable("LoginScreen"){
                            LoginScreen(navController = navController)
                        }
                        composable("RegisterScreen"){
                            RegisterScreen(navController = navController)
                        }
                        composable("HomePage"){
                            HomePage(navController = navController)
                        }
                        composable("UserDetails"){
                            UserDetails(navController = navController)
                        }
                        composable("Capture"){
                            CaptureScreen(navController = navController)
                        }
                        composable("Profile"){
                            Profile(navController = navController, viewModel = userDetailsViewModel)
                        }
                        composable("ArticleOne"){
                            ArticleOne(navController = navController)
                        }
                        composable("ArticleTwo"){
                            ArticleTwo(navController = navController)
                        }
                        composable("ArticleThree"){
                            ArticleThree(navController = navController)
                        }
                        composable("ArticleFour"){
                            ArticleFour(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
