package com.example.database

import com.example.database.ProductTable.autoIncrement
import org.jetbrains.exposed.sql.Table

object StoreTable: Table("stores") {

    val id = integer("id").autoIncrement()
    val storeId = varchar("storeId", 200)
    val lat = varchar("lat", 100)
    val lng = varchar("lng", 100)
    val storeName = varchar("storeName", 100)
    override val primaryKey = PrimaryKey(id)

}