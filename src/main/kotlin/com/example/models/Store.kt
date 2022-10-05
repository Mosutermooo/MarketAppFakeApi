package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Store(
    val storeId: String,
    val storeName: String,
    val lat: String,
    val lng: String,
)
