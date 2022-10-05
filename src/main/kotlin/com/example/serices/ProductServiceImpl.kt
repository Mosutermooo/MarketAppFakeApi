package com.example.serices

import com.example.database.Database.dbQuery
import com.example.database.ProductImages
import com.example.database.ProductTable
import com.example.models.AddProductModel
import com.example.models.Product
import com.example.models.ProductImage
import com.example.models.ProductResponseModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ProductServiceImpl : ProductService {

    override suspend fun addProduct(params: AddProductModel): ProductResponseModel {
        val product =  dbQuery{
            ProductTable.select{
                ProductTable.name.eq(params.name)
            }.map {
                rowToProduct(it)
            }.singleOrNull()
        }
        if(product != null){
            return ProductResponseModel("Product already exists with that store", false)
        }

        val result =  dbQuery {
            ProductTable.insert {
                it[productId] = randomId()
                it[barcode] = params.barcode
                it[store] = params.store
                it[name] = params.name
                it[price] = params.price
            }
        }
        dbQuery {
            val newProduct = ProductTable.select{
                ProductTable.name.eq(params.name)
                ProductTable.store.eq(params.store)
            }.map {
                rowToProduct(it)
            }.singleOrNull()

            params.images.forEach {image ->
                ProductImages.insert {
                    it[productId] = newProduct?.productId!!
                    it[ProductImages.image] = image
                }
            }
        }

        if(result.insertedCount == 1){
            return ProductResponseModel(
                "Product successfully added",
                true
            )
        }

        return ProductResponseModel(
            "Failed to add product",
            false
        )




    }


    private fun rowToProduct(row: ResultRow?): Product? {
        return if(row == null) null
            else Product(
            productId = row[ProductTable.productId],
            barcode = row[ProductTable.barcode],
            name = row[ProductTable.name],
            store = row[ProductTable.store],
            price = row[ProductTable.price],
            images = productImages(row[ProductTable.productId])
            )
    }

    private fun productImages(productId: String): List<ProductImage?>{
        return transaction {
            ProductImages.select{ProductImages.productId.eq(productId)}
                .map {
                    rowToImage(it)
                }
        }
    }

    private fun rowToImage(row: ResultRow?): ProductImage? {
        return if(row == null) null
        else ProductImage(
            imageId = row[ProductImages.imageId],
            productId = row[ProductImages.productId],
            image = row[ProductImages.image]
        )
    }
    private fun randomId(): String {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(20) { alphabet.random() }.joinToString("")
    }


}