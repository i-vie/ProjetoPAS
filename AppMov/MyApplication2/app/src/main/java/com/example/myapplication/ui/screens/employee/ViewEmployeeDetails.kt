package com.example.myapplication

/*
//screen to see the details of a selected employee
@Composable
fun EmployeeDetailsScreen(
    navController: NavController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val selectedEmployee by viewModel::selectedEmployee
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
                    Text(
                        text = selectedEmployee.id.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Text(
                        text = selectedEmployee.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Text(
                        text = selectedEmployee.username,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    Text(
                        text = selectedEmployee.email,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.padding(8.dp)
                    )
                    val text = if (selectedEmployee.admin) {
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
            if (!selectedEmployee.admin) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            viewModel.deactivateEmployee(selectedEmployee.id)
                            navController.navigate(GourmetManagerScreen.Employees.name)
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
                            text = stringResource(id = R.string.delete),
                            color = MaterialTheme.colorScheme.onTertiary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.makeEmployeeAdmin(selectedEmployee.id)
                            navController.navigate(GourmetManagerScreen.ViewEmployeeDetails.name)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(end = 16.dp)

                    ) {
                        Text(
                            text = stringResource(id = R.string.make_administrator),
                            color = MaterialTheme.colorScheme.onTertiary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}


 */