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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.myapplication.ui.models.Category
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.MenuViewModel

//screen to edit a category
@Composable
fun EditCategoryScreen(
    navController: NavController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    //observes the currently selected category from the viewModel
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    //mutable state for the category name input
    var name by remember { mutableStateOf(selectedCategory.name) }
    //mutable state to validate the name input
    var isNameValid by remember { mutableStateOf(true) }

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
            NameField(
                name = name,
                isNameValid = isNameValid,
                label = stringResource(id = R.string.category_name),
                onValueChange = {
                    name = it
                    isNameValid = it.isNotBlank()
                })

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                selectedCategory.let {
                    EditCategoryButton(
                        enabled = isNameValid,
                        name = name,
                        category = it,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }

        }
    }
}

@Composable
fun EditCategoryButton(
    enabled: Boolean,
    name: String,
    category: Category,
    navController: NavController,
    viewModel: MenuViewModel
) {

    Button(
        onClick = {
            val updatedCategory = Category(category.id, name)
            viewModel.updateCategory(updatedCategory)
            navController.navigate(GourmetManagerScreen.Menu.name)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface
        ),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.change),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}


@Preview
@Composable
fun EditCategoryPreview() {
    GourmetManagerTheme {
        EditCategoryScreen(
            navController = rememberNavController(),
            viewModel = MenuViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )


    }
}