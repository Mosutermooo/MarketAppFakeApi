package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseModel (
    val message: String,
    val success: Boolean,
    val products: List<Product?>? = null
        )