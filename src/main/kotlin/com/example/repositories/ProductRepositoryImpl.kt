package com.example.repositories

import com.example.models.AddProductModel
import com.example.models.ProductResponseModel
import com.example.serices.ProductService

class ProductRepositoryImpl(private val service: ProductService): ProductRepository {
    override suspend fun addProduct(params: AddProductModel): ProductResponseModel {
        return service.addProduct(params)
    }
}