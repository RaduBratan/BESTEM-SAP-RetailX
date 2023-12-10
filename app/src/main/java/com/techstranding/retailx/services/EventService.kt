package com.techstranding.retailx.services

import com.techstranding.retailx.models.Event
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventService @Inject constructor(
    private val storageService: StorageService
) {
    suspend fun fetchEvents(): List<Event> {
        return storageService.getDocuments(
            collection = "events",
            documentFields = arrayOf(
                "name",
                "demandedProductsIds",
            ),
        )
    }
}