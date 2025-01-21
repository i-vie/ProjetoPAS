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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.Poppins
import com.example.myapplication.ui.viewmodels.AppViewModel

//screen to see the number of tables of the restaurant and to change it
@Composable
fun TablesScreen(
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
/*
    val totalTables by viewModel.totalTables.collectAsState()
    var numberOfTables by remember { mutableStateOf("") }
    var isNumberValid by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getTotalTables()
    }

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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "${totalTables}" + stringResource(id = R.string.available_tables),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = modifier.padding(16.dp)
            )
            NumberField(
                numberOfTables = numberOfTables,
                isNumberValid = isNumberValid,
                onValueChange = {
                    numberOfTables = it
                    isNumberValid = try {
                        it.toInt()
                        true
                    } catch (e: NumberFormatException) {
                        false
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                SaveTablesButton(
                    enabled = isNumberValid,
                    numberOfTables = try {
                        numberOfTables.toInt()
                    } catch (e: NumberFormatException) {
                        0
                    },
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun SaveTablesButton(
    enabled: Boolean,
    numberOfTables: Int,
    navController: NavController,
    viewModel: AppViewModel
) {
    val showDialog = remember { mutableStateOf(false) }
    val clicked = remember { mutableStateOf(false) }
    Button(
        onClick = {
            clicked.value = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
        ),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.save),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    if (clicked.value) {
        viewModel.updateTotalTables(numberOfTables)
        showDialog.value = true
        clicked.value = false
    }
    if (showDialog.value) {
        TablesDialog(
            numberOfTables = numberOfTables,
            onDismissRequest = {
                showDialog.value = false
                navController.navigate(GourmetManagerScreen.Home.name)
            },
        )
    }
}

@Composable
fun NumberField(numberOfTables: String, isNumberValid: Boolean, onValueChange: (String) -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = numberOfTables,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.nr_tables),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = if (isNumberValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                unfocusedIndicatorColor = if (isNumberValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (!isNumberValid && numberOfTables.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.invalid_number),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun TablesDialog(
    numberOfTables: Int,
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
                        stringResource(id = R.string.updated_tables_success),
                        numberOfTables
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

 */
}