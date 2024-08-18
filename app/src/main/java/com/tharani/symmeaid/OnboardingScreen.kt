package com.tharani.symmeaid

import android.graphics.Paint.Align
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {
    val images = listOf(
        R.drawable.onboarding_one,  // Replace with your jpg or png resource
        R.drawable.onboarding_two   // Replace with your jpg or png resource
    )
    val titles = listOf(
        "Discover the \n harmony of \n your unique \n expression",
        "Refresh your \n natural look"
    )
    val pagerState = rememberPagerState(
        pageCount = images.size
    )
    val gradient = Brush.linearGradient(
        0.0f to Color.Black,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(gradient),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            Modifier.wrapContentSize()
        ) { currentPage ->
            Column(
                Modifier
                    .wrapContentSize()
                    .padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = images[currentPage]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Text(
                    text = titles[currentPage],
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
        }

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(60.dp)
        )
    }

    ButtonsSection(
        pagerState = pagerState,
        navController = navController
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ButtonsSection(pagerState: com.google.accompanist.pager.PagerState, navController: NavHostController) {

    val scope = rememberCoroutineScope()
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)){
        Button(onClick = {
            navController.popBackStack()
            navController.navigate("LoginScreen")
        },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(260.dp)
                .height(55.dp)) {
            Text(text = "Continue",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ){
        repeat(pageCount){
            IndicatorSingleDot(isSelected = it == currentPage )
        }
    }

}

@Composable
fun IndicatorSingleDot(isSelected: Boolean) {

    // val width = animateDpAsState(targetValue = if(isSelected) 15.dp else 10.dp, label = "")
    // val height = animateDpAsState(targetValue = if(isSelected) 15.dp else 10.dp, label = "")
    Box(modifier = Modifier
        .padding(2.dp)
        .height(10.dp) //height.value
        .width(10.dp)  //width.value
        .clip(CircleShape)
        .background(if (isSelected) Color(0xFFFFFFFF) else Color(0x83F5BABA))
    )
}
