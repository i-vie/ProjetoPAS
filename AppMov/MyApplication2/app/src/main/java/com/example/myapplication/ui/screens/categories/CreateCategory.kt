package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.MenuViewModel


//screen to create a new category
@Composable
fun CreateCategoryScreen(
    navController: NavController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    var categoryName by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(true) }

    //checks if form is valid (name is not blank)
    val isFormValid = categoryName.isNotBlank()

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
            Spacer(modifier = Modifier.height(16.dp))
            //category name input field
            CategoryField(
                name = categoryName,
                isNameValid = isNameValid,
                onValueChange = {
                    categoryName = it
                    isNameValid = it.isNotBlank() //validates name
                })
            Spacer(modifier = Modifier.height(16.dp))
            //button to add the new category
            AddNewCategoryButton(
                enabled = isFormValid, //enables button only if form is valid
                name = categoryName,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

//category name input field
@Composable
fun CategoryField(name: String, isNameValid: Boolean, onValueChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = name,
        onValueChange = onValueChange, //updates the name value
        label = {
            Text(
                text = stringResource(id = R.string.new_category),
                color = MaterialTheme.colorScheme.inversePrimary
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = if (isNameValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
            unfocusedIndicatorColor = if (isNameValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        singleLine = true
    )
}

//button to add the new category
@Composable
fun AddNewCategoryButton(
    enabled: Boolean,
    name: String,
    viewModel: MenuViewModel,
    navController: NavController
) {
    Button(
        onClick = {
            viewModel.addCategory(name = name) //calls method to add category
            navController.navigate(GourmetManagerScreen.Menu.name) //navigates back to menu
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
        ),
        enabled = enabled, //enables or disables the button
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_category),
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Preview
@Composable
fun CreateCategoryPreview() {
    GourmetManagerTheme {
        CreateCategoryScreen(
            navController = rememberNavController(),
            viewModel = MenuViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}