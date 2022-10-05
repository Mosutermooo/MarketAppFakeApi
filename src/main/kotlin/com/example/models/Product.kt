package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productId: String,
    val barcode: String,
    val name: String,
    val store: String,
    val price: String,
    val images: List<ProductImage?>
)
