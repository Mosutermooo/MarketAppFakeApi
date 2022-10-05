package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class GetProductsWithBarcodeParams(
    val barcode: String,
)
