package com.example.myapplication.ui.screens.categories

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.GourmetManagerScreen
import com.example.myapplication.R
import com.example.myapplication.ui.models.Category
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.MenuViewModel

//menu screen with the available categories
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    val categoriesList by viewModel.categoryList.collectAsState()

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val buttonModifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                Button(
                    onClick = {
                        navController.navigate(GourmetManagerScreen.CreateCategory.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = buttonModifier
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.category),
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(GourmetManagerScreen.CreateProduct.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = buttonModifier
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.new_product),
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            categoriesList.forEach { item: Category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardEditCategory(
                        category = item,
                        onClick = ({
                            viewModel.selectCategory(item)
                            navController.navigate(GourmetManagerScreen.ShowProducts.name)
                        }),
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CardEditCategory(
    category: Category,
    onClick: () -> Unit,
    navController: NavController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.weight(0.7f)
            )
            IconButton(
                onClick = {
                    viewModel.selectCategory(category)
                    navController.navigate(GourmetManagerScreen.EditCategory.name)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(id = R.string.change),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            IconButton(
                onClick = {
                    showDialog.value = true
                },
                modifier = Modifier
                    .size(36.dp)
                    .weight(0.2f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
    if (showDialog.value) {
        DeleteCategoryDialog(
            category = category,
            onDismissRequest = { showDialog.value = false },
            onConfirmation = {
                //we decided not to delete categories and products
                //so that there is no issues with the orders
                //although these categories and products are no longer available
                viewModel.deactivateCategory(category)
                showDialog.value = false
            }
        )
    }
}

@Composable
fun DeleteCategoryDialog(
    category: Category,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {

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
                        stringResource(id = R.string.delete_category_message),
                        category.name
                    ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(16.dp),
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
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            stringResource(id = R.string.confirm),
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
fun MenuScreenPreview() {
    GourmetManagerTheme {
        MenuScreen(
            navController = rememberNavController(),
            viewModel = MenuViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}