package com.example.database

import org.jetbrains.exposed.sql.Table

object ProductImages : Table("product_images"){

    val imageId = integer("imageId").autoIncrement()
    val productId = varchar("productId", 100)
    val image = varchar("image", 10000)
    override val primaryKey: PrimaryKey = PrimaryKey(imageId)

}