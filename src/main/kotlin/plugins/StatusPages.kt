package me.basehub.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.*
import me.basehub.common.exception.ApiException
import me.basehub.common.response.respondError

fun Application.configureStatusPages() {
    install(StatusPages) {

        // application error
        exception<ApiException> { call, cause ->
            call.respondError(
                httpStatus = cause.statusCode,
                message = cause.message
            )
        }

        // Request validation Error
        exception<RequestValidationException> { call, cause ->
            call.respondError(
                httpStatus = HttpStatusCode.BadRequest,
                message = cause.reasons.joinToString(", ")
            )
        }

        // Malformed request bodies
        exception<ContentTransformationException> { call, _ ->
            call.respondError(
                httpStatus = HttpStatusCode.BadRequest,
                message = "Invalid request body"
            )
        }

        // Bad request
        exception<BadRequestException> { call, _ ->
            call.respondError(
                httpStatus = HttpStatusCode.BadRequest,
                message = "Invalid request"
            )
        }

        // common HTTP errors.
        status(
            HttpStatusCode.Unauthorized,
            HttpStatusCode.Forbidden,
            HttpStatusCode.NotFound,
            HttpStatusCode.Conflict,
            HttpStatusCode.TooManyRequests,
            HttpStatusCode.ServiceUnavailable
        ) { call, status ->
            val message = when (status) {
                HttpStatusCode.Unauthorized -> "Authentication required"
                HttpStatusCode.Forbidden -> "Access forbidden"
                HttpStatusCode.NotFound -> "Resource not found"
                HttpStatusCode.Conflict -> "Resource conflict"
                HttpStatusCode.TooManyRequests -> "Too many requests"
                HttpStatusCode.ServiceUnavailable -> "Service unavailable"
                else -> "Request failed"
            }
            call.respondError(
                httpStatus = status,
                message = message
            )
        }

        // Hides unexpected internal errors.
        exception<Throwable> { call, cause ->
            this@configureStatusPages.log.error(
                "Unhandled exception",
                cause
            )
            call.respondError(
                httpStatus = HttpStatusCode.InternalServerError,
                message = "Internal server error"
            )
        }
    }
}
