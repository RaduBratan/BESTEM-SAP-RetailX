package com.techstranding.retailx.ui.products

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techstranding.retailx.R
import com.techstranding.retailx.models.Product
import com.techstranding.retailx.ui.theme.noFontPadding
import com.techstranding.retailx.ui.theme.plusJakartaSans
import com.techstranding.retailx.ui.views.ErrorMessage
import com.techstranding.retailx.ui.views.LoadingAnimation
import com.techstranding.retailx.ui.views.SectionHeader

const val GRID_SIZE = 2

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductsScreen(
    // modifier: Modifier = Modifier,
    viewModel: ProductsViewModel = hiltViewModel(),
    onAnomaliesSeeAllClick: () -> Unit,
    onDemandsSeeAllClick: () -> Unit,
) {
    val obtainedProductsState = viewModel.obtainedProductsState.collectAsState()
    val isRefreshing = viewModel.isRefreshing.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            if (obtainedProductsState.value !is ObtainedProductsState.Loading) {
                viewModel.getData()
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getData(canRefresh = false)
    }

    Box(Modifier.pullRefresh(pullRefreshState)) {
        when (obtainedProductsState.value) {
            is ObtainedProductsState.Loading -> {
                LoadingAnimation(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is ObtainedProductsState.Error -> {
                ErrorMessage(
                    message = stringResource(
                        id = R.string.products_get_data_error_message
                    ),
                    isInHomeScreen = true,
                    onBackClick = { }
                )
            }

            is ObtainedProductsState.Success -> {
                val products =
                    (obtainedProductsState.value as ObtainedProductsState.Success).products

                ProductsView(
                    products = products,
                    onAnomaliesSeeAllClick = onAnomaliesSeeAllClick,
                    onDemandsSeeAllClick = onDemandsSeeAllClick,
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ProductsView(
    products: ProductsItemViewModel,
    onAnomaliesSeeAllClick: () -> Unit,
    onDemandsSeeAllClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        /*item(span = { GridItemSpan(GRID_SIZE) }) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.products_title),
                fontFamily = plusJakartaSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    platformStyle = noFontPadding,
                    letterSpacing = 0.15.sp,
                ),
            )
        }

        item(span = { GridItemSpan(GRID_SIZE) }) {
            SectionHeader(
                text = stringResource(id = R.string.products_section_10_anomalies_title),
                onClick = null,
            )
        }*/

        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.products_title),
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
            style = TextStyle(
                platformStyle = noFontPadding,
                letterSpacing = 0.15.sp,
            ),
        )

        SectionHeader(
            text = stringResource(id = R.string.products_section_10_anomalies_title),
            onClick = null,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SIZE),
        ) {
            items(products.tenAnomalyProducts.size) { productIndex ->
                val modifier = when {
                    productIndex % 2 == 0 -> Modifier.padding(
                        start = 24.dp,
                        end = 6.dp,
                        bottom = 12.dp,
                    )

                    productIndex % 2 == 1 -> Modifier.padding(
                        start = 6.dp,
                        end = 24.dp,
                        bottom = 12.dp,
                    )

                    else -> Modifier
                }

                val product = products.tenAnomalyProducts[productIndex]

                ProductCard(
                    modifier = modifier,
                    product = product,
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(56.dp))
                .clickable(onClick = onAnomaliesSeeAllClick)
                .padding(vertical = 12.dp),
            text = stringResource(id = R.string.products_button_see_all_title),
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = TextStyle(
                platformStyle = noFontPadding,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.Center,
            ),
        )

        SectionHeader(
            text = stringResource(id = R.string.products_section_10_demands_title),
            onClick = null,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SIZE),
        ) {
            items(products.tenDemandedProducts.size) { productIndex ->
                val modifier = when {
                    productIndex % 2 == 0 -> Modifier.padding(
                        start = 24.dp,
                        end = 6.dp,
                        bottom = 12.dp,
                    )

                    productIndex % 2 == 1 -> Modifier.padding(
                        start = 6.dp,
                        end = 24.dp,
                        bottom = 12.dp,
                    )

                    else -> Modifier
                }

                val product = products.tenDemandedProducts[productIndex]

                ProductCard(
                    modifier = modifier,
                    product = product,
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(56.dp))
                .clickable(onClick = onDemandsSeeAllClick)
                .padding(vertical = 12.dp),
            text = stringResource(id = R.string.products_button_see_all_title),
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = TextStyle(
                platformStyle = noFontPadding,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .width(148.dp)
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .size(64.dp),
            imageVector = Icons.Filled.Headphones,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomStart),
            text = product.description,
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White,
            style = TextStyle(
                platformStyle = noFontPadding,
                letterSpacing = 0.25.sp,
            ),
        )
    }
}