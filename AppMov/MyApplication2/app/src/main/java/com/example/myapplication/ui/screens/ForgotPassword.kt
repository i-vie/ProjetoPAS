package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myapplication.ui.viewmodels.AppViewModel
import kotlinx.coroutines.delay

/*
//screen to recover the user's password
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isUsernameValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }

    val isFormValid =
        username.isNotBlank() && email.isNotBlank()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.fill_details),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(top = 32.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            NameField(
                name = username,
                label = stringResource(id = R.string.username),
                isNameValid = isUsernameValid,
                onValueChange = {
                    username = it
                    isUsernameValid = it.isNotBlank()
                })
            Spacer(modifier = Modifier.height(16.dp))
            NameField(
                name = email,
                label = stringResource(id = R.string.email),
                isNameValid = isEmailValid,
                onValueChange = {
                    email = it
                    isEmailValid = it.isNotBlank()
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            RecoverPasswordButton(
                enabled = isFormValid,
                username = username,
                email = email,
                onUsernameInvalid = { isUsernameValid = false },
                onEmailIncorrect = { isEmailValid = false },
                onRecoverySuccess = {
                    isUsernameValid = true
                    isEmailValid = true
                },
                navController = navController,
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate(GourmetManagerScreen.Login.name) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.back_button),
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun RecoverPasswordButton(
    enabled: Boolean,
    username: String,
    email: String,
    onUsernameInvalid: () -> Unit,
    onEmailIncorrect: () -> Unit,
    onRecoverySuccess: () -> Unit,
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {

    val clicked = remember { mutableStateOf(false) }
    val password by viewModel.passwordResult.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    Button(
        onClick = {
            clicked.value = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
        ),
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.recover_password),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
    if (clicked.value) {
        LaunchedEffect(clicked) {
            viewModel.checkUsername(username)
            delay(100)
            viewModel.checkEmail(username)
            delay(100)
            clicked.value = false
        }

        val employeeResult by viewModel.employeeResult.collectAsState()
        val emailCheckResult by viewModel.emailCheckResult.collectAsState()


        LaunchedEffect(employeeResult, emailCheckResult) {
            when {
                username.isBlank() -> {
                    onUsernameInvalid()
                }

                employeeResult == null -> {
                    onUsernameInvalid()
                }

                email.isBlank() -> {
                    onEmailIncorrect()
                }

                emailCheckResult == false -> {
                    onEmailIncorrect()
                }

                else -> {
                    viewModel.getEmployeePassword(username, email)
                    onRecoverySuccess()
                    showDialog.value = true
                }
            }
        }
    }
    if (showDialog.value) {
        ShowPasswordDialog(
            password = password,
            onDismissRequest = {
                showDialog.value = false
                navController.navigate(GourmetManagerScreen.Login.name)
            }
        )
    }
}

@Composable
fun ShowPasswordDialog(
    password: String,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { })
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = String.format(
                        stringResource(id = R.string.your_password), password
                    ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            stringResource(id = R.string.ok),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

 */