package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.entity.EmployeeEntity
import com.example.myapplication.ui.viewmodels.AppViewModel

/*
//screen to see the list of active employees
@Composable
fun EmployeesScreen(
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val employeeList by viewModel.employeeList.collectAsState()

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
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            ) {

                //header
                item {
                    EmployeeTableHeaderRow()
                }
                items(employeeList) { item ->
                    EmployeeTableRow(item, navController, viewModel)

                }
            }

        }
    }
}

@Composable
fun EmployeeTableHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.id),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = stringResource(id = R.string.name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = "",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.weight(0.2f)
        )
    }
}

@Composable
fun EmployeeTableRow(
    employee: EmployeeEntity,
    navController: NavController,
    viewModel: AppViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = employee.id.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .weight(0.3f)
                .padding(start = 8.dp)
        )
        Text(
            text = employee.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(0.5f)
        )
        IconButton(
            onClick = {
                viewModel.updateSelectedEmployee(employee)
                navController.navigate(GourmetManagerScreen.ViewEmployeeDetails.name)
            },
            modifier = Modifier.weight(0.3f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.eye),
                contentDescription = stringResource(id = R.string.change),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

    }
}

 */
