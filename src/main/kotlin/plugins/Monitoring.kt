package me.basehub.plugins

import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.CallLogging
import org.slf4j.event.Level

fun Application.configureMonitoring() {
    /*
    *
    * Add unique id for one request
    *
    */
    install(CallId) {
        header(HttpHeaders.XRequestId)
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
    /*
    *
    * Write and show methode, Url, status response, and request activity
    *
    */
    install(CallLogging){
        level = Level.INFO
        callIdMdc("call-id")
    }
}