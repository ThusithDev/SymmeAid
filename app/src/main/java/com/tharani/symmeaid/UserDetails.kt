package com.tharani.symmeaid

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.tharani.symmeaid.viewModel.UserDetailsViewModel

@Composable
fun UserDetails(navController: NavHostController) {
    val gradient = Brush.linearGradient(
        0.0f to Color.Black,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    val viewModel: UserDetailsViewModel = viewModel()
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(gradient),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Details",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 100.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(text = "Age", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            textStyle = TextStyle(color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                Toast.makeText(
                    navController.context,
                    "Button Clicked!",
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.saveUserDetails(name, age) { success ->
                    if (success) {
                        navController.popBackStack()
                        navController.navigate("Capture")
                    } else {
                        Toast.makeText(
                            navController.context,
                            "An error occurred while saving your details.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 6.dp
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .width(260.dp)
                .height(55.dp)
        ) {
            Text(
                text = "Continue",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}