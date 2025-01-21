package com.example.myapplication

import android.app.Application
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.theme.Poppins
import com.example.myapplication.ui.viewmodels.AppViewModel

/*
//screen to create an account
@Composable
fun CreateAccountScreen(
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(true) }
    var isUsernameValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }

    val isFormValid =
        name.isNotBlank() && username.isNotBlank() && email.isNotBlank() && password.length >= 5 && !password.contains(
            " "
        )

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
                name = name,
                label = stringResource(id = R.string.name),
                isNameValid = isNameValid,
                onValueChange = {
                    name = it
                    isNameValid = it.isNotBlank()
                })
            Spacer(modifier = Modifier.height(16.dp))
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
            Spacer(modifier = Modifier.height(16.dp))
            PasswordField(
                password = password,
                isPasswordValid = isPasswordValid,
                onValueChange = {
                    password = it
                    isPasswordValid = it.length >= 5 && !it.contains(" ")
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            CreateAccountButton(
                enabled = isFormValid,
                name = name,
                username = username,
                email = email,
                password = password,
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
fun PasswordField(
    password: String,
    isPasswordValid: Boolean,
    onValueChange: (String) -> Unit
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.onPrimaryContainer),
        value = password,
        onValueChange = onValueChange,
        label = {
            Text(
                stringResource(id = R.string.password),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = if (isPasswordValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
            unfocusedIndicatorColor = if (isPasswordValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
    )
}

@Composable
fun CreateAccountButton(
    enabled: Boolean,
    name: String,
    username: String,
    email: String,
    password: String,
    navController: NavController,
    viewModel: AppViewModel,
) {
    val usernameExists by viewModel.usernameExists.collectAsState()
    val emailExists by viewModel.emailExists.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val created = remember { mutableStateOf(false) }

    LaunchedEffect(username) {
        viewModel.usernameExists(username)
        viewModel.emailExists(email)
    }
    Button(
        onClick = {
            if (!usernameExists && !emailExists) {
                viewModel.addEmployee(
                    name = name,
                    username = username,
                    email = email,
                    password = password,
                    admin = false
                )
                showDialog.value = true
                created.value = true
            } else {
                showDialog.value = true
                created.value = false
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
        ),
        enabled = enabled,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.create_account),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (showDialog.value) {
        ShowAccountDialog(
            created = created.value,
            onDismissRequest = {
                showDialog.value = false
                if (created.value) {
                    navController.navigate(GourmetManagerScreen.Login.name)
                }
            }
        )
    }
}

@Composable
fun ShowAccountDialog(
    created: Boolean,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() })
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
                    text = if (created) {
                        stringResource(id = R.string.account_created_success)
                    } else {
                        stringResource(id = R.string.existing_username)
                    },
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


@Preview
@Composable
fun CreateAccountPreview() {
    GourmetManagerTheme {
        CreateAccountScreen(
            navController = rememberNavController(),
            viewModel = AppViewModel(Application()),
            modifier = Modifier
                .fillMaxSize()
        )
    }


}

 */