package com.example.serices

import com.example.models.AddProductModel
import com.example.models.GetProductsWithBarcodeParams
import com.example.models.ProductResponseModel

interface ProductService {
    suspend fun addProduct(params: AddProductModel): ProductResponseModel
    suspend fun getProductsWithBarcode(barcode: String?): ProductResponseModel


}