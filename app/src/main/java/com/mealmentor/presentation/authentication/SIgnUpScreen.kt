package com.mealmentor.presentation.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mealmentor.R
import com.mealmentor.presentation.CustomTextField
import com.mealmentor.presentation.PasswordField
import com.mealmentor.presentation.Toast
import com.mealmentor.ui.theme.ButtonText
import com.mealmentor.ui.theme.MainTitle
import com.mealmentor.ui.theme.SmallTitle
import com.mealmentor.ui.theme.SmallTitleBold
import com.mealmentor.util.Response
import com.mealmentor.util.Screens

// LoginPage це функція, яка містить розмітку сторінки входу в додаток
@Composable
fun SignUpScreen(navController: NavHostController, viewModel: AuthenticationViewModel) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MainTitle
        )
        Text(
            text = stringResource(id = R.string.sign_up_text),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = SmallTitle
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var usernameText by remember { mutableStateOf("") }
        var emailText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }
        var password2Text by remember { mutableStateOf("") }
        var passwordLength by remember { mutableStateOf(false) }

        CustomTextField(
            fieldLabel = stringResource(id = R.string.username),
            icon = Icons.Default.AccountCircle,
            text = usernameText,
            onValueChange = {
                usernameText = it
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        CustomTextField(
            fieldLabel = stringResource(id = R.string.email),
            icon = Icons.Default.Email,
            text = emailText,
            onValueChange = {
                emailText = it
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        PasswordField(
            fieldLabel = stringResource(id = R.string.password),
            text = passwordText,
            onValueChange = {
                passwordText = it
                passwordLength = it.length < 6
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        PasswordField(
            fieldLabel = stringResource(id = R.string.confirm_password),
            text = password2Text,
            onValueChange = {
                password2Text = it
                passwordLength = it.length < 6
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        ElevatedButton(
            onClick = {
                viewModel.signUp(
                    name = usernameText,
                    email = emailText,
                    password = passwordText
                )
            },
            shape = ButtonDefaults.elevatedShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 3.dp,
                pressedElevation = 0.dp
            ),
            modifier = Modifier
                .padding(horizontal = 80.dp)
                .height(65.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                style = ButtonText
            )
            when (val response = viewModel.signUpState.value) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    if (response.data) {
                        navController.navigate(Screens.Main.route) {
                            popUpTo(Screens.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast(stringResource(id = R.string.sign_up_failed))
                    }
                }

                is Response.Error -> {
                    Toast(message = response.message)
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 5.dp))

        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(id = R.string.have_account),
                color = MaterialTheme.colorScheme.onPrimary,
                style = SmallTitle
            )

            Text(
                text = stringResource(id = R.string.login),
                color = MaterialTheme.colorScheme.onPrimary,
                style = SmallTitleBold,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable {
                        navController.navigate(route = Screens.LoginScreen.route) {
                            launchSingleTop = true
                        }
                    }
            )
        }
    }
}
