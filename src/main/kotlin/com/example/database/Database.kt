package com.example.database

import com.example.uils.Constants.dbUrl
import com.example.uils.Constants.driver
import com.example.uils.Constants.password
import com.example.uils.Constants.user
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    fun connect(){
        Database.connect(
            url = dbUrl,
            driver = driver,
            user = user,
            password = password
        )

        transaction {
            SchemaUtils.create(ProductTable)
            SchemaUtils.create(ProductImages)
        }

    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO){
        transaction {
            block()
        }
    }
}