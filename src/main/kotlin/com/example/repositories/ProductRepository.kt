package com.example.repositories

import com.example.models.AddProductModel
import com.example.models.ProductResponseModel

interface ProductRepository {
    suspend fun addProduct(params: AddProductModel): ProductResponseModel
}