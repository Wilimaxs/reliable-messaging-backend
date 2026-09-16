package me.basehub

import io.ktor.server.application.Application
import me.basehub.handlers.HealthHandler
import me.basehub.plugins.configureDatabase
import me.basehub.plugins.configureMonitoring
import me.basehub.plugins.configureRequestValidation
import me.basehub.plugins.configureSecurity
import me.basehub.plugins.configureSerialization
import me.basehub.plugins.configureStatusPages
import me.basehub.plugins.configureWebSockets
import me.basehub.routes.configureRouting

fun Application.module() {
    val datasource = configureDatabase()

    val healthHandler = HealthHandler(
        dataSource = datasource,
    )

    configureMonitoring()
    configureSerialization()

    configureStatusPages()
    configureRequestValidation()
    configureSecurity()
    configureWebSockets()

    configureRouting(
        healthHandler = healthHandler,
    )
}