package com.example.repositories

import com.example.models.AddProductModel
import com.example.models.GetProductsWithBarcodeParams
import com.example.models.ProductResponseModel

interface ProductRepository {
    suspend fun addProduct(params: AddProductModel): ProductResponseModel
    suspend fun getProductsWithBarcode(barcode: String?): ProductResponseModel
}