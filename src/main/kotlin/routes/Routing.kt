package me.basehub.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.basehub.handlers.HealthHandler

fun Application.configureRouting(
    healthHandler: HealthHandler,
) {
    routing {
        get("/health") {
            healthHandler.health(call)
        }

        get("/ready") {
            healthHandler.ready(call)
        }
    }
}