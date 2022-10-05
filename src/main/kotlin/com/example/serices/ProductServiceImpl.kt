package com.example.serices

import com.example.database.Database.dbQuery
import com.example.database.ProductImages
import com.example.database.ProductTable
import com.example.database.StoreTable
import com.example.models.*
import kotlinx.coroutines.selects.select
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.StringBuilder

class ProductServiceImpl : ProductService {

    override suspend fun addProduct(params: AddProductModel): ProductResponseModel {
        val product = dbQuery{
            ProductTable.select{
                ProductTable.description.eq(params.description)
            }.map {
                rowToProduct(it)
            }.singleOrNull()
        }

        val store = dbQuery {
            StoreTable.select {
                StoreTable.storeName.eq(params.storeName)
            }.map {
                rowToStore(it)
            }.singleOrNull()
        }

        val randomId = randomId()
        val storeId = storeId()



        if(store == null){
           dbQuery {
               StoreTable.insert {
                   it[storeName] = params.storeName
                   it[StoreTable.storeId] = storeId
                   it[lat] = params.storeLat
                   it[lng] = params.storeLng
               }
           }
        }


        val result = dbQuery {
            ProductTable.insert {
                it[productId] = randomId
                it[barcode] = params.barcode
                it[ProductTable.storeId] = store?.storeId ?: storeId
                it[name] = params.name
                it[price] = params.price
                it[company] = params.company
                it[description] = params.description
            }

        }

        dbQuery {
            params.images.forEach {image ->
                ProductImages.insert {
                    it[productId] = randomId
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

    override suspend fun getProductsWithBarcode(params: GetProductsWithBarcodeParams): ProductResponseModel {
        val products = dbQuery {
            ProductTable.select {
                ProductTable.barcode.eq(params.barcode)
            }.map {
                rowToProduct(it)
            }
        }

        if(products.isEmpty()){
            return ProductResponseModel("Doesn't exist in our database", false)
        }



        return ProductResponseModel(
            "Products",
            true,
            products
        )





    }


    private fun rowToProduct(row: ResultRow?): Product? {
        return if(row == null) null
            else Product(
            productId = row[ProductTable.productId],
            barcode = row[ProductTable.barcode],
            name = row[ProductTable.name],
            store = row[ProductTable.storeId],
            price = row[ProductTable.price],
            images = productImages(row[ProductTable.productId]),
            productStore = getStore(row[ProductTable.storeId])
            )
    }

    private fun getStore(storeId: String): Store {
        val store =  transaction {
            StoreTable.select {
                StoreTable.storeId.eq(storeId)
            }.map {
                rowToStore(it)
            }.single()
        }
        return store!!
    }

    private fun rowToStore(row: ResultRow?): Store? {
        return if(row == null) null
        else Store(
            row[StoreTable.storeId],
            row[StoreTable.storeName],
            row[StoreTable.lat],
            row[StoreTable.lng],
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

    private fun storeId(): String {
        val sb = StringBuilder()
        val currentMillis = System.currentTimeMillis().toString()
        return sb.append("STORE").append(currentMillis).toString()
    }


}