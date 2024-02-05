package com.mealmentor.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mealmentor.R
import com.mealmentor.logic.database.sign_in.SignInState
import com.mealmentor.ui.screens.elements.CustomTextField
import com.mealmentor.ui.screens.elements.PasswordField

// LoginPage це функція, яка містить розмітку сторінки входу в додаток
@Composable
fun SignUpPage(
    state: SignInState,
    onGoogleSignInClick: () -> Unit,
    navigateToLogInPage: () -> Unit
) {

    // Отримання контексту
    val context = LocalContext.current

    // Чотири функції remember для збереження стану текстового поля
    val usernameText = remember {
        mutableStateOf("")
    }

    val emailText = remember {
        mutableStateOf("")
    }

    val passwordText = remember {
        mutableStateOf("")
    }

    val password2Text = remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CustomTextField(
                fieldLabel = stringResource(id = R.string.username),
                leadingIconId = R.drawable.user,
                text = usernameText.value,
                onValueChange = {
                    usernameText.value = it
                }
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            CustomTextField(
                fieldLabel = stringResource(id = R.string.email),
                leadingIconId = R.drawable.email,
                text = emailText.value,
                onValueChange = {
                    emailText.value = it
                }
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            PasswordField(
                fieldLabel = stringResource(id = R.string.password),
                text = passwordText.value,
                onValueChange = {
                    passwordText.value = it
                }
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            PasswordField(
                fieldLabel = stringResource(id = R.string.confirm_password),
                text = password2Text.value,
                onValueChange = {
                    password2Text.value = it
                }
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            ElevatedButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Login",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                shape = ButtonDefaults.elevatedShape,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 0.dp
                ),
                modifier = Modifier
                    .padding(horizontal = 80.dp)
                    .height(65.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            Row(
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(id = R.string.have_account),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall
                )

                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            navigateToLogInPage()
                        }
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Meal Mentor",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.sign_up_text),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall
            )
        }

        // Кнопка "Увійти з Google"
        ElevatedButton(
            onClick = {
                onGoogleSignInClick()
            },
            shape = ButtonDefaults.elevatedShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 0.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .height(65.dp)
                .align(Alignment.BottomCenter),
            contentPadding = PaddingValues(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "google_img",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = stringResource(id = R.string.login_with_google),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
