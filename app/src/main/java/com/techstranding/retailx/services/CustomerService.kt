package com.techstranding.retailx.services

import com.techstranding.retailx.models.Customer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerService @Inject constructor(
    private val storageService: StorageService
) {
    suspend fun fetchCustomers(): List<Customer> {
        return storageService.getDocuments(
            collection = "customers",
            documentFields = arrayOf(
                "id",
                "recommendedProductsIds"
            ),
        )
    }
}
