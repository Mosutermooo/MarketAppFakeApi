package com.example.database

import org.jetbrains.exposed.sql.Table

object ProductTable : Table("products") {

    val id = integer("id").autoIncrement()
    val productId = varchar("productId", 100)
    val barcode = varchar("barcode", 10000)
    val name = varchar("name", 2000)
    val store = varchar("store", 2000)
    val price = varchar("price", 2000)
    override val primaryKey: PrimaryKey = PrimaryKey(id)


}