package com.project.kasirku.presentation.Splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.project.kasirku.R
import com.project.kasirku.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(
        key1 = true,
        block = {
            delay(1500L)
            navController.navigate(Screen.Masuk.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    )
    SplashScreenContent(modifier)
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.logo_kasirku)


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = image,
            contentDescription = "logo kasir",
        )
    }
}
