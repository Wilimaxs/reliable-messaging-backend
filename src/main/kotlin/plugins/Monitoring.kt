package me.basehub.plugins

import io.ktor.http.HttpHeaders
import io.ktor.server.application.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.CallLogging
import org.slf4j.event.Level
import java.util.UUID.randomUUID

fun Application.configureMonitoring() {
    /*
    *
    * Add unique id for one request
    *
    */
    install(CallId) {
        generate {
            randomUUID().toString()
        }
        replyToHeader(HttpHeaders.XRequestId)
    }
    /*
    *
    * Write and show methode, Url, status response, and request activity
    *
    */
    install(CallLogging) {
        level = Level.INFO
        callIdMdc("call-id")
    }
}