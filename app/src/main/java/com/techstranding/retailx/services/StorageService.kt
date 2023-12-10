package com.techstranding.retailx.services

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageService @Inject constructor(
) {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    fun getCurrentUser() = auth.currentUser

    suspend inline fun <reified T : Any> getDocuments(
        collection: String,
        documentFields: Array<String>,
        filters: Map<String, Any> = emptyMap()
    ): List<T> {
        val db = Firebase.firestore
        var query: Query = db.collection(collection)

        for ((field, value) in filters) {
            query = query.whereEqualTo(field, value)
        }

        val querySnapshot = query.get().await()
        val list = mutableListOf<T>()

        for (document in querySnapshot.documents) {
            val objectFields = mutableMapOf<String, Any>()

            for (documentField in documentFields) {
                val value = document.get(documentField)

                if (value != null) {
                    objectFields[documentField] = value
                }
            }

            val obj = convertToObject<T>(objectFields)

            list.add(obj)
        }

        return list
    }

    inline fun <reified T : Any> convertToObject(fieldMap: Map<String, Any>): T {
        return Gson().fromJson(Gson().toJson(fieldMap), T::class.java)
    }
}