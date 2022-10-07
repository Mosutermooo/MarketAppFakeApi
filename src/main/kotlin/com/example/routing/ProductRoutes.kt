package com.example.routing

import com.example.models.AddProductModel
import com.example.models.GetProductsWithBarcodeParams
import com.example.repositories.ProductRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.productRoutes(productRepository: ProductRepository) {
    routing {
        route("/product"){
            post("/add"){
                val params = call.receive<AddProductModel>()
                val result = productRepository.addProduct(params)
                call.respond(result)
            }
            get("/get") {
                val barcode = call.request.queryParameters["barcode"]
                val result = productRepository.getProductsWithBarcode(barcode)
                call.respond(result)
            }

        }
    }
}