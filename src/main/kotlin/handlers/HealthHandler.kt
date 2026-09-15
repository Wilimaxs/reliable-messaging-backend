package me.basehub.handlers

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import javax.sql.DataSource

class HealthHandler(
    private val dataSource: DataSource,
) {
    suspend fun health(call: ApplicationCall) {
        call.respond(
            status = HttpStatusCode.OK,
            message = HealthResponse(status = "up"),
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
            call.respond(
                status = HttpStatusCode.OK,
                message = HealthResponse(status = "ready"),
            )
        } else {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = HealthResponse(status = "not found")
            )
        }
    }
}

@Serializable
data class HealthResponse(val status: String)