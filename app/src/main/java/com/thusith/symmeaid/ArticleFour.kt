package com.thusith.symmeaid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun ArticleFour(navController: NavHostController) {
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
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Nutritional Strategies for Supporting Natural Healing", modifier = Modifier.padding(start = 12.dp), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "A balanced diet plays a crucial role in supporting the body's natural healing processes. Consuming a\n" +
                            "variety of nutrient-rich foods can enhance immune function, reduce inflammation, and promote overall\n" +
                            "health. Focus on incorporating fruits, vegetables, whole grains, and lean proteins into your meals. Foods\n" +
                            "rich in antioxidants, such as berries, leafy greens, and nuts, help combat oxidative stress and support\n" +
                            "cellular repair.\n \n" +
                            "Omega-3 fatty acids, found in fatty fish, like salmon and seeds, have anti-inflammatory properties that\n" +
                            "can aid in reducing chronic inflammation. Additionally, staying hydrated by drinking plenty of water is\n" +
                            "essential for maintaining bodily functions and facilitating detoxification. Avoiding excessive sugar and\n" +
                            "processed foods can also contribute to better health outcomes.\n \n" +
                            "By making mindful nutritional choices, you can support your body’s natural ability to heal and function\n" +
                            "optimally. For personalized advice, consider consulting with a registered dietitian or nutritionist to create\n" +
                            "a dietary plan that aligns with your individual health needs and goals.", modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

    }
}

