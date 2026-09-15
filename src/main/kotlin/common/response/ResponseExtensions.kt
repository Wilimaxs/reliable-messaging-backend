package me.basehub.common.response

import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend inline fun <reified T> ApplicationCall.respondSuccess(
    message: String,
    data: T?,
    meta: PaginationMeta? = null
) {
    respond(
        ApiResponse(
            status = true,
            message = message,
            data = data,
            meta = meta
        )
    )
}

suspend inline fun ApplicationCall.respondSuccess(
    message: String,
    meta: PaginationMeta? = null
) {
    respond(
        ApiResponse(
            status = true,
            message = message,
            data = null,
            meta = meta
        )
    )
}

suspend inline fun ApplicationCall.respondError(
    message: String,
    meta: PaginationMeta? = null
) {
    respond(
        ApiResponse(
            status = false,
            message = message,
            data = null,
            meta = meta
        )
    )
}



