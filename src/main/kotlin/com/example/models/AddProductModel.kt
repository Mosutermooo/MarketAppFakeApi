package com.example.models

import com.example.database.ProductTable
import kotlinx.serialization.Serializable

@Serializable
data class AddProductModel (
    val barcode: String,
    val name: String,
    val store: String,
    val price: String,
    val images: List<String>
        )