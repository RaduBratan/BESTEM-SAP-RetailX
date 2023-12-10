package com.techstranding.retailx.models

data class Product (
    val id: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val anomalyLevel: Double,
    val demandLevel: Double
)