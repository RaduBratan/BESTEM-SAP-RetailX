package com.techstranding.retailx.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techstranding.retailx.models.Product
import com.techstranding.retailx.services.ProductService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productService: ProductService,
) : ViewModel() {
    val obtainedProductsState =
        MutableStateFlow<ObtainedProductsState>(ObtainedProductsState.Loading)
    val isRefreshing = MutableStateFlow(false)

    fun getData(
        canRefresh: Boolean = true,
    ) {
        viewModelScope.launch {
            try {
                if (canRefresh)
                    isRefreshing.value = true

                val products = productService.fetchProducts()

                obtainedProductsState.value =
                    ObtainedProductsState.Success(ProductsItemViewModel(products))
            } catch (e: Exception) {
                if (canRefresh)
                    isRefreshing.value = true

                obtainedProductsState.value = ObtainedProductsState.Error
            } finally {
                if (canRefresh)
                    isRefreshing.value = false
            }
        }
    }
}

sealed class ObtainedProductsState {
    object Loading : ObtainedProductsState()

    data class Success(
        val products: ProductsItemViewModel,
    ) : ObtainedProductsState()

    object Error : ObtainedProductsState()
}

data class ProductsItemViewModel(
    private val products: List<Product>
) {
    val tenAnomalyProducts = products.sortedBy { product -> product.anomalyLevel }.take(10)
    val tenDemandedProducts = products.sortedBy { product -> product.demandLevel }.take(10)

    val allAnomalyProducts = products.sortedBy { product -> product.anomalyLevel }
    val inverseAllAnomalyProducts = products.sortedByDescending { product -> product.anomalyLevel }

    val allDemandedProducts = products.sortedBy { product -> product.demandLevel }
    val inverseAllDemandedProducts = products.sortedByDescending { product -> product.demandLevel }
}