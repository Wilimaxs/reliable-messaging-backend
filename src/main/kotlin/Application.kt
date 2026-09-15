package me.basehub

import io.ktor.server.application.Application
import me.basehub.handlers.HealthHandler
import me.basehub.plugins.configureDatabase
import me.basehub.plugins.configureMonitoring
import me.basehub.plugins.configureSerialization
import me.basehub.routes.configureRouting

fun Application.module() {
    val datasource = configureDatabase()

    val healthHandler = HealthHandler(
        dataSource = datasource,
    )

    configureDatabase()
    configureMonitoring()
    configureSerialization()

    configureStatusPages()
    configureRequestValidation()
    configureRateLimiting()

    configureSecurity()
    configureWebsockets()

    configureRouting(
        healthHandler = healthHandler,
    )
}