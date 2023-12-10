package com.techstranding.retailx.models

data class Customer (
    val id: String,
    val recommendedProductsIds: List<String>
)