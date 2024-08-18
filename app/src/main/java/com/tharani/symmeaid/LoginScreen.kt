package com.tharani.symmeaid

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import com.tharani.symmeaid.viewModel.RegisterViewModel

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: RegisterViewModel = viewModel()
    val gradient = Brush.linearGradient(
        0.0f to Color.Black,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(gradient),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 100.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", color = Color.White) },
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
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            textStyle = TextStyle(color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Button(
                onClick = {
                    isLoading = true
                    viewModel.loginUser(email, password) { success, message ->
                        isLoading = false
                        if (success) {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            navController.popBackStack()
                            navController.navigate("HomePage")
                        } else {
                            errorMessage = message
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
                    .padding(top = 40.dp)
                    .width(260.dp)
                    .height(55.dp)
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        ClickableRegisterText(navController, onTextSelected = {})

        errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = message,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
fun ClickableRegisterText(navController: NavHostController, onTextSelected: (String) -> Unit){
    val initialText = "Don't have an account? "
    val registerText = " Sign Up"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Black)) {
            pushStringAnnotation(tag = registerText, annotation = registerText)
            append(registerText)
        }
    }

    ClickableText(text = annotatedString, onClick = {
        // Your navigation logic
        navController.popBackStack()
        navController.navigate("RegisterScreen")
    })
}


