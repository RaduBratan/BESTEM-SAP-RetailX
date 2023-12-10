package com.techstranding.retailx.services

import com.techstranding.retailx.models.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductService @Inject constructor(
    private val storageService: StorageService
) {
    suspend fun fetchProducts(): List<Product> {
        return storageService.getDocuments(
            collection = "products",
            documentFields = arrayOf(
                "id",
                "description",
                "price",
                "stock",
                "anomalyLevel",
                "demandLevel"
            ),
        )
    }
}