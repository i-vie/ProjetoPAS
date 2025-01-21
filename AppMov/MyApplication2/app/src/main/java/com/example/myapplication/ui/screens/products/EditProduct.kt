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
import com.example.myapplication.ui.models.Product
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.MenuViewModel

//screen to edit a product (name, price or description)
@Composable
fun EditProductScreen(
    navController: NavController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    var name by remember { mutableStateOf(selectedProduct?.name ?: "") }
    var price by remember { mutableStateOf(selectedProduct?.price.toString()) }
    var description by remember { mutableStateOf(selectedProduct?.description ?: "") }
    var isNameValid by remember { mutableStateOf(true) }
    var isPriceValid by remember { mutableStateOf(true) }
    var selectedCategoryID by remember { mutableStateOf(selectedProduct?.categoryId ?: 0) }

    val isFormValid = name.isNotBlank() && try {
        price.toDouble()
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
            DescriptionField(description = description) { description = it }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val priceValue = try {
                    price.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
                val updatedProduct =
                    selectedProduct?.let {
                        Product(
                            it.id,
                            name,
                            priceValue,
                            description,
                            selectedCategoryID,
                            1
                        )
                    }
                if (updatedProduct != null) {
                    EditProductButton(
                        enabled = isFormValid,
                        updatedProduct = updatedProduct,
                        navController = navController,
                        viewModel = viewModel
                    )
                }

            }

        }
    }
}

@Composable
fun EditProductButton(
    enabled: Boolean,
    updatedProduct: Product,
    navController: NavController,
    viewModel: MenuViewModel
) {

    Button(
        onClick = {
            viewModel.updateProduct(updatedProduct)
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
fun EditProductPreview() {
    GourmetManagerTheme {
        EditProductScreen(
            navController = rememberNavController(),
            viewModel = MenuViewModel(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}