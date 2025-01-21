package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.viewmodels.AppViewModel

//current employee profile screen
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(true) {
        viewModel.getLoggedInEmployee()
    }
    val loggedInEmployee by viewModel.loggedInEmployee.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.Start
    ) {
        Column(Modifier.padding(16.dp)) {
            Row {
                Column {
                    Spacer(
                        modifier
                            .padding(16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.employeeNr),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.name),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.username),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.email),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.admin),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Spacer(modifier.padding(24.dp))
                }
                Column {
                    Spacer(
                        modifier
                            .padding(16.dp)
                    )
                    if (loggedInEmployee != null) {
                        Text(
                            text = "${loggedInEmployee!!.id}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = modifier.padding(8.dp)
                        )
                    }
                    if (loggedInEmployee != null) {
                        Text(
                            text = loggedInEmployee!!.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = modifier.padding(8.dp)
                        )
                    }
                    if (loggedInEmployee != null) {
                        Text(
                            text = loggedInEmployee!!.username,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = modifier.padding(8.dp)
                        )
                    }
                    if (loggedInEmployee != null) {
                        Text(
                            text = loggedInEmployee!!.email,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = modifier.padding(8.dp)
                        )
                    }
                    val text = if (loggedInEmployee?.admin == 1) {
                        stringResource(id = R.string.yes)
                    } else {
                        stringResource(id = R.string.no)
                    }
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Spacer(modifier.padding(24.dp))
                }
            }
            /*
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(GourmetManagerScreen.ChangePassword.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 16.dp)

                ) {
                    Text(
                        text = stringResource(id = R.string.change_password),
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            */
        }
    }
}