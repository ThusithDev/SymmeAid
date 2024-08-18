package com.tharani.symmeaid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
//import com.airbnb.lottie.compose.LottieAnimation
//import com.airbnb.lottie.compose.LottieCompositionSpec
//import com.airbnb.lottie.compose.LottieConstants
//import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(key1 = true){
        delay(800)
        navController.navigate("HomeScreen")
    }

    val gradient = Brush.linearGradient(
        0.0f to Color.Black,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
        , verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
        LoaderAnimation(
            modifier = Modifier. size(600.dp)
            ,anim = R.raw.splash_screen_v
        )
        **/
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "",
            fontSize = 32.sp)
    }
}

/**
@Composable
fun LoaderAnimation(modifier: Modifier, anim: Int) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(anim))

    LottieAnimation(
        composition = composition, iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}
 **/