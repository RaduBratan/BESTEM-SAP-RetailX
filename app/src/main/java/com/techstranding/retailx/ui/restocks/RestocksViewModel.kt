package com.techstranding.retailx.ui.restocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techstranding.retailx.models.Event
import com.techstranding.retailx.models.Product
import com.techstranding.retailx.services.EventService
import com.techstranding.retailx.services.ProductService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestocksViewModel @Inject constructor(
    private val productService: ProductService,
    private val eventService: EventService,
) : ViewModel() {
    val obtainedRestocksState =
        MutableStateFlow<ObtainedRestocksState>(ObtainedRestocksState.Loading)
    val isRefreshing = MutableStateFlow(false)

    fun getData(
        canRefresh: Boolean = true,
    ) {
        viewModelScope.launch {
            try {
                if (canRefresh)
                    isRefreshing.value = true

                val events = eventService.fetchEvents()
                val products = productService.fetchProducts()
                val demandedProducts = mutableListOf<Product>()

                events.forEach { event ->
                    products.filterTo(demandedProducts) { product ->
                        event.demandedProductsIds.contains(product.id)
                    }
                }

                obtainedRestocksState.value = ObtainedRestocksState.Success(
                    events,
                    demandedProducts
                )
            } catch (e: Exception) {
                if (canRefresh)
                    isRefreshing.value = true

                obtainedRestocksState.value = ObtainedRestocksState.Error
            } finally {
                if (canRefresh)
                    isRefreshing.value = false
            }
        }
    }
}

sealed class ObtainedRestocksState {
    object Loading : ObtainedRestocksState()

    data class Success(
        val events: List<Event>,
        val products: List<Product>,
    ) : ObtainedRestocksState()

    object Error : ObtainedRestocksState()
}