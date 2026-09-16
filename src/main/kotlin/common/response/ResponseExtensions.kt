package me.basehub.common.response

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend inline fun <reified T> ApplicationCall.respondSuccess(
    message: String,
    data: T?,
    httpStatus: HttpStatusCode = HttpStatusCode.OK,
    meta: PaginationMeta? = null
) {
    respond(
        status = httpStatus,
        message = ApiResponse(
            status = true,
            message = message,
            data = data,
            meta = meta
        )
    )
}

suspend fun ApplicationCall.respondSuccess(
    message: String,
    httpStatus: HttpStatusCode = HttpStatusCode.OK,
    meta: PaginationMeta? = null
) {
    respond(
        status = httpStatus,
        message = ApiResponse<Unit>(
            status = true,
            message = message,
            data = null,
            meta = meta
        )
    )
}

suspend inline fun ApplicationCall.respondError(
    httpStatus: HttpStatusCode,
    message: String,
    meta: PaginationMeta? = null
) {
    respond(
        status = httpStatus,
        message = ApiResponse(
            status = false,
            message = message,
            data = null,
            meta = meta
        )
    )
}



