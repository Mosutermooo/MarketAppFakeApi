package com.example.models

import com.example.database.ProductImages
import com.example.database.ProductImages.autoIncrement
import kotlinx.serialization.Serializable

@Serializable
data class ProductImage (
        val imageId : Int,
        val productId : String,
        val image : String
        )