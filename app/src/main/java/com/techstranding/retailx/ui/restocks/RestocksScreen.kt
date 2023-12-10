package com.techstranding.retailx.ui.restocks

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
import com.techstranding.retailx.models.Event
import com.techstranding.retailx.models.Product
import com.techstranding.retailx.ui.products.GRID_SIZE
import com.techstranding.retailx.ui.products.ProductCard
import com.techstranding.retailx.ui.views.ErrorMessage
import com.techstranding.retailx.ui.views.LoadingAnimation
import com.techstranding.retailx.ui.views.SectionHeader

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RestocksScreen(
    // modifier: Modifier = Modifier,
    viewModel: RestocksViewModel = hiltViewModel(),
) {
    val obtainedRestocksState = viewModel.obtainedRestocksState.collectAsState()
    val isRefreshing = viewModel.isRefreshing.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            if (obtainedRestocksState.value !is ObtainedRestocksState.Loading) {
                viewModel.getData()
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getData(canRefresh = false)
    }

    Box(Modifier.pullRefresh(pullRefreshState)) {
        when (obtainedRestocksState.value) {
            is ObtainedRestocksState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is ObtainedRestocksState.Error -> {
                ErrorMessage(
                    message = stringResource(
                        id = R.string.restocks_get_data_error_message
                    ),
                    isInHomeScreen = true,
                    onBackClick = { }
                )
            }

            is ObtainedRestocksState.Success -> {
                val events =
                    (obtainedRestocksState.value as ObtainedRestocksState.Success).events
                val products =
                    (obtainedRestocksState.value as ObtainedRestocksState.Success).products

                RestocksView(
                    events = events,
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
private fun RestocksView(
    events: List<Event>,
    products: List<Product>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_SIZE),
    ) {
        items(events.size) { eventIndex ->
            val event = events[eventIndex]

            RestocksSection(
                event = event,
                products = products,
            )
        }
    }
}

@Composable
private fun RestocksSection(
    event: Event,
    products: List<Product>,
) {
    SectionHeader(
        modifier = Modifier.padding(top = 12.dp),
        text = event.name,
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