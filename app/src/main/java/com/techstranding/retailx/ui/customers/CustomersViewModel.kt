package com.techstranding.retailx.ui.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techstranding.retailx.models.Customer
import com.techstranding.retailx.models.Product
import com.techstranding.retailx.services.CustomerService
import com.techstranding.retailx.services.ProductService
import com.techstranding.retailx.services.UserService
import com.techstranding.retailx.ui.products.ProductsItemViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val customerService: CustomerService,
    private val productService: ProductService,
) : ViewModel() {
    val obtainedCustomersState =
        MutableStateFlow<ObtainedCustomersState>(ObtainedCustomersState.Loading)
    val isRefreshing = MutableStateFlow(false)

    fun getData(
        canRefresh: Boolean = true,
    ) {
        viewModelScope.launch {
            try {
                if (canRefresh)
                    isRefreshing.value = true

                val customers = customerService.fetchCustomers()
                val products = productService.fetchProducts()
                val recommendedProducts = mutableListOf<Product>()

                customers.forEach { customer ->
                    products.filterTo(recommendedProducts) { product ->
                        customer.recommendedProductsIds.contains(product.id)
                    }
                }

                obtainedCustomersState.value = ObtainedCustomersState.Success(
                    customers,
                    recommendedProducts,
                )
            } catch (e: Exception) {
                if (canRefresh)
                    isRefreshing.value = true

                obtainedCustomersState.value = ObtainedCustomersState.Error
            } finally {
                if (canRefresh)
                    isRefreshing.value = false
            }
        }
    }
}

sealed class ObtainedCustomersState {
    object Loading : ObtainedCustomersState()

    data class Success(
        val customers: List<Customer>,
        val products: List<Product>,
    ) : ObtainedCustomersState()

    object Error : ObtainedCustomersState()
}