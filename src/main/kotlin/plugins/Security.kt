package me.basehub.plugins

import io.ktor.server.application.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import me.basehub.common.response.respondError

const val JWT_AUTH_PROVIDER = "auth-jwt"

fun Application.configureSecurity() {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

    require(jwtSecret.length >= 32) {
        "JWT secret must contain at least 32 characters"
    }

    authentication {
        jwt(JWT_AUTH_PROVIDER) {
            realm = jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .withAudience(jwtAudience)
                    .acceptLeeway(3)
                    .build()
            )
            validate { credential ->
                val userId = credential.payload.subject
                val tokenType = credential.payload.getClaim("type").asString()
                val expiresAt = credential.expiresAt
                if (!userId.isNullOrBlank() && tokenType == "access" && expiresAt != null)
                    JWTPrincipal(credential.payload)
                else
                    null
            }
            challenge { _, _ ->
                call.respondError(
                    httpStatus = HttpStatusCode.Unauthorized,
                    message = "Invalid token"
                )
            }
        }
    }
}