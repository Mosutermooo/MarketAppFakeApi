package com.example.routing

import com.example.repositories.ProductRepository
import com.example.repositories.ProductRepositoryImpl
import com.example.serices.ProductService
import com.example.serices.ProductServiceImpl
import io.ktor.server.application.*

fun Application.configureRouting(){

    val productService : ProductService = ProductServiceImpl()
    val productRepository: ProductRepository = ProductRepositoryImpl(productService)
    productRoutes(productRepository)

}