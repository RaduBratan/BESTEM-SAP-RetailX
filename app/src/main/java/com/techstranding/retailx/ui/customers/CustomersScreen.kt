package com.techstranding.retailx.ui.customers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techstranding.retailx.R
import com.techstranding.retailx.models.Customer
import com.techstranding.retailx.models.Product
import com.techstranding.retailx.ui.products.GRID_SIZE
import com.techstranding.retailx.ui.products.ProductCard
import com.techstranding.retailx.ui.views.ErrorMessage
import com.techstranding.retailx.ui.views.LoadingAnimation
import com.techstranding.retailx.ui.views.SectionHeader

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomersScreen(
    // modifier: Modifier = Modifier,
    viewModel: CustomersViewModel = hiltViewModel(),
) {
    val obtainedCustomersState = viewModel.obtainedCustomersState.collectAsState()
    val isRefreshing = viewModel.isRefreshing.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            if (obtainedCustomersState.value !is ObtainedCustomersState.Loading) {
                viewModel.getData()
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getData(canRefresh = false)
    }

    Box(Modifier.pullRefresh(pullRefreshState)) {
        when (obtainedCustomersState.value) {
            is ObtainedCustomersState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is ObtainedCustomersState.Error -> {
                ErrorMessage(
                    message = stringResource(
                        id = R.string.restocks_get_data_error_message
                    ),
                    isInHomeScreen = true,
                    onBackClick = { }
                )
            }

            is ObtainedCustomersState.Success -> {
                val customers =
                    (obtainedCustomersState.value as ObtainedCustomersState.Success).customers
                val products =
                    (obtainedCustomersState.value as ObtainedCustomersState.Success).products

                CustomersView(
                    customers = customers,
                    products = products,
                )
            }
        }

        PullRefreshIndicator(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
            refreshing = isRefreshing.value,
            state = pullRefreshState,
        )
    }
}

@Composable
private fun CustomersView(
    customers: List<Customer>,
    products: List<Product>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_SIZE),
    ) {
        items(customers.size) { customerIndex ->
            val customer = customers[customerIndex]

            RestocksSection(
                customer = customer,
                products = products,
            )
        }
    }
}

@Composable
private fun RestocksSection(
    customer: Customer,
    products: List<Product>,
) {
    SectionHeader(
        modifier = Modifier.padding(top = 12.dp),
        text = customer.id,
        onClick = null,
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(products.size) { productIndex ->
            val product = products[productIndex]

            ProductCard(
                product = product,
            )
        }
    }
}