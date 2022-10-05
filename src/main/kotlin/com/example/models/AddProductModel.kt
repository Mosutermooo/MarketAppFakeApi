package com.example.models

import com.example.database.ProductTable
import kotlinx.serialization.Serializable

@Serializable
data class AddProductModel (
    val barcode: String,
    val name: String,
    val description: String,
    val company: String,
    val storeName: String,
    val price: String,
    val storeLat: String,
    val storeLng: String,
    val images: List<String>
        )