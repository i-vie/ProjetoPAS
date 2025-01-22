package com.example.myapplication


import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import com.example.myapplication.ui.viewmodels.AppViewModel

import com.example.myapplication.ui.theme.Poppins
import com.example.myapplication.ui.theme.grey
import com.example.myapplication.ui.theme.white
import com.example.myapplication.ui.theme.blue
import com.example.myapplication.ui.theme.yellow
import kotlinx.coroutines.delay

//login screen
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUsernameValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }


    Box(
    ){
        Canvas(modifier = Modifier.fillMaxSize()) {

                val background = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(0f, size.height)
                    lineTo(size.width, size.height)
                    lineTo(size.width, 0f)
                    lineTo(0f, 0f)
                    close()
                }
                drawPath(background, color = white)
            val path = Path().apply {
                moveTo(0f, size.height * 0.85f)
                lineTo(0f, size.height)
                lineTo(size.width, size.height)
                lineTo(size.width, 0f)
                lineTo(2000f, 150f)
                close()
            }
            drawPath(path, color = yellow)
        }
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            // Language Selector Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LanguageSelector(90.dp, changeColor = false)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Image(
                    painter = painterResource(R.drawable.logo_vertical),
                    contentDescription = null,
                    modifier = Modifier.width(300.dp)
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.padding_medium)
                )
            ) {
                UsernameTextField(
                    username,
                    isUsernameValid,
                    onValueChange = {
                        username = it
                        isUsernameValid = true
                    })
                PasswordTextField(
                    password,
                    isPasswordValid,
                    onValueChange = {
                        password = it
                        isPasswordValid = true
                    } )
                LoginButton(
                    username = username,
                    password = password,
                    onUsernameInvalid = { isUsernameValid = false },
                    onPasswordIncorrect = { isPasswordValid = false },
                    onLoginSuccess = {
                        isUsernameValid = true
                        isPasswordValid = true
                        navController.navigate(GourmetManagerScreen.Home.name)
                    },
                    viewModel = viewModel)
            }
            Spacer(modifier = Modifier.height(16.dp))
            /* Not implemented at this stage
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ForgotPassword(
                    onClick = {
                        navController.navigate(GourmetManagerScreen.ForgotPassword.name)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SignUp(
                    onClick = {
                        navController.navigate(GourmetManagerScreen.CreateAccount.name)
                    }
                )
            }
            */
        }
    }
}

//button to go to the password recovery screen
@Composable
fun ForgotPassword(
    onClick: (Int) -> Unit
) {
    Text(stringResource(id = R.string.forgot_password), color = white)
    ClickableText(
        onClick = onClick,
        text = AnnotatedString(
            stringResource(id = R.string.click_here)),
        style = TextStyle(color = white, fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp)
    )
}



//button to login in the app
@Composable
fun LoginButton(
    username: String,
    password: String,
    onUsernameInvalid: () -> Unit,
    onPasswordIncorrect: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {


    var clicked by remember { mutableStateOf(false) }
    var isRequestInProgress by remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (!isRequestInProgress) {
                isRequestInProgress = true
                clicked = true
            }
},
        colors = ButtonDefaults.buttonColors(blue),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(56.dp)
            .width(280.dp)
    ) {
        Text(
            stringResource(id = R.string.sign_in),
            style = MaterialTheme.typography.labelSmall,
            color = white)
    }
    if (clicked) {
        LaunchedEffect(clicked) {
            viewModel.login(username, null, password)
            clicked = false
        }

        val loginResult by viewModel.loginResult.collectAsState()
        Log.d("Login", "$loginResult")
        val errorMessage by viewModel.errorMessage.observeAsState()
        Log.d("Login message", "$errorMessage")

        LaunchedEffect(loginResult, errorMessage) {
            when {
                username.isBlank() -> {
                    onUsernameInvalid()
                }
                loginResult == null -> {
                    onUsernameInvalid()
                }
                password.isBlank() -> {
                    onPasswordIncorrect()
                }
                errorMessage != null -> {
                    onPasswordIncorrect()
                }
                else -> {
                    val loggedInEmployee = loginResult!!
                    viewModel.setLoggedInEmployee(loggedInEmployee)
                    onLoginSuccess()
                }
            }
            isRequestInProgress = false
        }
    }
}

@Composable
fun PasswordTextField(password: String, isPasswordValid: Boolean, onValueChange : (String) -> Unit) {


    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .width(280.dp),
            value = password,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(id = R.string.password), color = grey, fontFamily = Poppins,
                    fontWeight = FontWeight.Normal
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = blue,
                unfocusedTextColor = blue,
                focusedContainerColor = white,
                unfocusedContainerColor = white,
                focusedLabelColor = if (isPasswordValid) grey else MaterialTheme.colorScheme.error,
                unfocusedLabelColor = if (isPasswordValid) grey else MaterialTheme.colorScheme.error,
                focusedBorderColor = if (isPasswordValid) yellow else MaterialTheme.colorScheme.error,
                unfocusedBorderColor = if (isPasswordValid) blue else MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(10.dp),
            maxLines = 1
        )
        LaunchedEffect (Unit) {
            delay(1000)
        }
        if (!isPasswordValid) {
            Text(
                text = stringResource(id = R.string.invalid_password),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun UsernameTextField(username: String, isUsernameValid: Boolean, onValueChange : (String) -> Unit) {

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .width(280.dp),
            value = username,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(id = R.string.user_email),
                    color = grey,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = blue,
                unfocusedTextColor = blue,
                focusedContainerColor = white,
                unfocusedContainerColor = white,
                focusedLabelColor = if (isUsernameValid) grey else MaterialTheme.colorScheme.error,
                unfocusedLabelColor = if (isUsernameValid) grey else MaterialTheme.colorScheme.error,
                focusedBorderColor = if (isUsernameValid) yellow else MaterialTheme.colorScheme.error,
                unfocusedBorderColor = if (isUsernameValid) blue else MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )
        LaunchedEffect (Unit) {
            delay(1000)
        }
        val userInvalidMessage = stringResource(id = R.string.user_invalid)
        LaunchedEffect(Unit) {
            if (isUsernameValid) {
                errorMessage = null
            } else {
                errorMessage = userInvalidMessage
            }
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }
        if (!isUsernameValid) {
            Text(
                text = stringResource(id = R.string.user_invalid),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }
    }
}

//button to go to the create account screen
@Composable
fun SignUp(
    onClick: (Int) -> Unit
) {
    Text(stringResource(id = R.string.no_account), color = white)
    ClickableText(
        onClick = onClick,
        text = AnnotatedString(stringResource(id = R.string.signup_here)),
        style = TextStyle(color = white, fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LanguageSelector(width: Dp, changeColor: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("PT") }
    val languages = listOf("PT", "EN")
    val context = LocalContext.current
    val backgroundColor: Color
    val textColor: Color

    if (changeColor) {
        textColor = MaterialTheme.colorScheme.onPrimaryContainer
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    } else {
        textColor = blue
        backgroundColor = white
    }
    Row (
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    painter = painterResource(id = R.drawable.language),
                    tint = textColor,
                    contentDescription = "Select Language"
                )
            }
        }
        Column {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .background(color = backgroundColor)
                    .width(width)
            ) {
                TextField(
                    modifier = Modifier.wrapContentWidth(),
                    value = selectedLanguage,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        cursorColor = textColor,
                        focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        disabledIndicatorColor = MaterialTheme.colorScheme.inversePrimary
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(color = backgroundColor)
                        .wrapContentWidth()
                ) {
                    languages.forEach { item ->
                        DropdownMenuItem(
                            {
                                Text(
                                    text = item,
                                    color = textColor,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            onClick = {
                                selectedLanguage = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                text = AnnotatedString(
                    text = stringResource(id = R.string.ok),
                    spanStyle = SpanStyle(
                        color = blue,
                        fontFamily = Poppins
                    )
                ),
                onClick = {
                    localeSelection(context, selectedLanguage)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

//function to change the app locale
fun localeSelection(context: Context, localeTag: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(localeTag)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(localeTag)
        )
    }
}
