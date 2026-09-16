package me.basehub.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        // Request validators are registered here.
    }
}
