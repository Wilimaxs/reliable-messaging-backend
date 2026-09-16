package me.basehub.plugins

import io.github.flaxoos.ktor.server.plugins.ratelimiter.*
import io.github.flaxoos.ktor.server.plugins.ratelimiter.implementations.*
import io.ktor.server.routing.*
import kotlin.time.Duration.Companion.seconds

fun Route.generalRateLimited(
    path: String,
    build: Route.() -> Unit
): Route = route(path) {
    install(RateLimiting) {
        rateLimiter {
            type = TokenBucket::class
            capacity = 60
            rate = 1.seconds
        }
    }

    build()
}