package me.basehub.common.exception

import io.ktor.http.HttpStatusCode

class ApiException (
    val statusCode: HttpStatusCode,
    override val message: String
): RuntimeException(message)