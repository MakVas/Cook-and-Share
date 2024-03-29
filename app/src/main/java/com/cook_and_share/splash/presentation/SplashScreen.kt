package com.cook_and_share.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cook_and_share.R
import com.cook_and_share.auth.presentation.AuthViewModel
import com.cook_and_share.core.presentation.ui.theme.Typography
import com.cook_and_share.core.presentation.util.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AuthViewModel?,
    navController: NavController
) {
    val authValue = viewModel?.currentUser != null

    LaunchedEffect(key1 = true) {
        delay(2000)
        if(authValue) {
            navController.navigate(Screens.Main.route){
                popUpTo(Screens.SplashScreen.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screens.LoginScreen.route){
                popUpTo(Screens.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    SplashScreenImage(
        modifier = Modifier
            .padding(vertical = 50.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )

}

@Composable
private fun SplashScreenImage(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name_caps),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = Typography.displayLarge
        )
        Image(
            painter = painterResource(id = R.drawable.splash_screen_img),
            contentDescription = "Cook&Share Logo"
        )
    }
}