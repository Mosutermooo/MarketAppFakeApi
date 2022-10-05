package com.example

import com.example.database.Database
import com.example.routing.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        Database.connect()
        install(ContentNegotiation){
            json()
        }
        configureRouting()
    }.start(wait = true)
}
