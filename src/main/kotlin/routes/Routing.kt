package me.basehub.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.basehub.handlers.HealthHandler
import me.basehub.plugins.generalRateLimited

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

        generalRateLimited("/api/v1") {
            // authRoutes(...)
            // conversationRoutes(...)
            // messageRoutes(...)
        }
    }
}