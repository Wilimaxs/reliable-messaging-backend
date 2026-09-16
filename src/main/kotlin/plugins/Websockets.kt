package me.basehub.plugins

import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

fun Application.configureWebSockets() {
    install(WebSockets) {
        // Serializes WebSocket events as JSON.
        contentConverter = KotlinxWebsocketSerializationConverter(
            Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                explicitNulls = false
            }
        )

        // Keeps inactive connections detectable.
        pingPeriod = 30.seconds
        timeout = 15.seconds

        // Prevents excessively large frames.
        maxFrameSize = 64 * 1024L

        // Server frames must not be masked.
        masking = false
    }
}