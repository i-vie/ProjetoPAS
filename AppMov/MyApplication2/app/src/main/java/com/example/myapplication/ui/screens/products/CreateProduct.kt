package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.models.Category
import com.example.myapplication.ui.models.Product
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.theme.Poppins
import com.example.myapplication.ui.viewmodels.MenuViewModel

//screen to create a new product
@Composable
fun CreateProductScreen(
    navController: NavController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(true) }
    var isPriceValid by remember { mutableStateOf(true) }
    var selectedCategoryID by remember { mutableStateOf(1) }

    val isFormValid = name.isNotBlank() && try {
        price.toFloat()
        true
    } catch (e: NumberFormatException) {
        false
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
        ) {
            Text(
                text = stringResource(R.string.fill_details),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(top = 32.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CategoryDropDownMenu(
                selectedCategoryID = selectedCategoryID,
                onCategorySelected = { selectedCategoryID = it },
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
            NameField(
                name = name,
                label = stringResource(id = R.string.product_name),
                isNameValid = isNameValid,
                onValueChange = {
                    name = it
                    isNameValid = it.isNotBlank()
                })
            Spacer(modifier = Modifier.height(16.dp))
            PriceField(
                price = price,
                isPriceValid = isPriceValid,
                onValueChange = {
                    price = it
                    isPriceValid = try {
                        it.toFloat()
                        true
                    } catch (e: NumberFormatException) {
                        false
                    }
                })
            Spacer(modifier = Modifier.height(16.dp))
            DescriptionField(description) { description = it }
            AddNewProductButton(
                enabled = isFormValid,
                name = name,
                price = try {
                    price.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                },
                description = description,
                categoryID = selectedCategoryID,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryDropDownMenu(
    selectedCategoryID: Int,
    onCategorySelected: (Int) -> Unit,
    viewModel: MenuViewModel
) {
    val categories by viewModel.categoryList.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    viewModel.getCategoryById(selectedCategoryID)
    viewModel.getCategories()
    val selectedCategory by viewModel.categoryResult.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            selectedCategory?.let {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = it.name,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledTextColor = MaterialTheme.colorScheme.inversePrimary,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        disabledIndicatorColor = MaterialTheme.colorScheme.inversePrimary,
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
            ) {
                categories.forEach { item: Category ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item.name,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onCategorySelected(item.id)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun NameField(
    name: String,
    label: String,
    isNameValid: Boolean,
    onValueChange: (String) -> Unit
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = name,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.inversePrimary,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = if (isNameValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
            unfocusedIndicatorColor = if (isNameValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
    )
}

@Composable
fun PriceField(price: String, isPriceValid: Boolean, onValueChange: (String) -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = price,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.price),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = if (isPriceValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                unfocusedIndicatorColor = if (isPriceValid) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (!isPriceValid && price.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.invalid_price),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun DescriptionField(description: String, onValueChange: (String) -> Unit) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = description,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(id = R.string.description),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.outline,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        ),
        maxLines = 3,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
    )
}

@Composable
fun AddNewProductButton(
    enabled: Boolean,
    name: String,
    price: Double,
    description: String,
    categoryID: Int,
    navController: NavController,
    viewModel: MenuViewModel
) {

    val product = Product(
        name = name,
        price = price,
        description = description,
        categoryId = categoryID,
        active = 1
    )


    Button(
        onClick = {
            viewModel.addProduct(product)
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
            text = stringResource(id = R.string.add_product),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
fun CreateProductPreview() {
    GourmetManagerTheme {
        CreateProductScreen(
            navController = rememberNavController(),
            viewModel = MenuViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}