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
import com.tharani.symmeaid.viewModel.FaceCaptureViewModel
import com.tharani.symmeaid.viewModel.RegisterViewModel
import com.tharani.symmeaid.viewModel.UserDetailsViewModel
import com.tharani.symmeaid.viewModel.ProgressViewModel

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
                    val registerViewModel: RegisterViewModel = viewModel()
                    val faceCaptureViewModel: FaceCaptureViewModel = viewModel()
                    val progressViewModel: ProgressViewModel = viewModel()

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
                            HomePage(navController = navController, progressViewModel = progressViewModel)
                        }
                        composable("UserDetails"){
                            UserDetails(navController = navController)
                        }
                        composable("Capture"){
                            CaptureScreen(navController = navController, faceCaptureViewModel = faceCaptureViewModel)
                        }
                        composable("CaptureTwo"){
                            CaptureScreenTwo(navController = navController, faceCaptureViewModel = faceCaptureViewModel)
                        }
                        composable("DisplayFace"){
                            DisplayImagesScreen(navController = navController, faceCaptureViewModel = faceCaptureViewModel)
                        }
                        composable("Profile"){
                            Profile(navController = navController, viewModel = userDetailsViewModel, registerViewModel = registerViewModel)
                        }
                        composable("Tutorials"){
                            Tutorials(navController = navController)
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
                        composable("ForgotPasswordScreen"){
                            ForgotPasswordScreen(navController = navController, registerViewModel = registerViewModel)
                        }
                    }
                }
            }
        }
    }
}
