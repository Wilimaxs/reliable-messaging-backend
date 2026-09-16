package me.basehub.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.basehub.common.response.respondError
import me.basehub.common.response.respondSuccess
import javax.sql.DataSource

class HealthHandler(
    private val dataSource: DataSource,
) {
    suspend fun health(call: ApplicationCall) {
        call.respondSuccess(
            message = "Up",
        )
    }

    suspend fun ready(call: ApplicationCall) {
        val databaseReady = withContext(Dispatchers.IO) {
            runCatching {
                dataSource.connection.use { connection ->
                    connection.prepareStatement("SELECT 1").use { statement ->
                        statement.executeQuery().use { result ->
                            result.next() && result.getInt(1) == 1
                        }
                    }
                }
            }.getOrDefault(false)
        }

        if (databaseReady) {
            call.respondSuccess(
                message = "Ready",
            )
        } else {
            call.respondError(
                httpStatus = HttpStatusCode.ServiceUnavailable,
                message = "Database is not ready"
            )
        }
    }
}