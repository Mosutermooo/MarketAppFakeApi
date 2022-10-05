package com.example.repositories

import com.example.models.AddProductModel
import com.example.models.GetProductsWithBarcodeParams
import com.example.models.ProductResponseModel
import com.example.serices.ProductService

class ProductRepositoryImpl(private val service: ProductService): ProductRepository {
    override suspend fun addProduct(params: AddProductModel): ProductResponseModel {
        return service.addProduct(params)
    }

    override suspend fun getProductsWithBarcode(params: GetProductsWithBarcodeParams): ProductResponseModel {
        return service.getProductsWithBarcode(params)
    }
}