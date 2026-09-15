package me.basehub.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped
import org.jetbrains.exposed.v1.jdbc.Database
import javax.sql.DataSource

fun Application.configureDatabase(): DataSource {
    val config = environment.config

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = config.property("database.url").getString()
        username = config.property("database.user").getString()
        password = config.property("database.password").getString()
        driverClassName = config.property("database.driver").getString()

        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    }
    val dataSource = HikariDataSource(hikariConfig)

    Database.connect(datasource = dataSource)

    monitor.subscribe(ApplicationStopped) {
        dataSource.close()
    }

    return dataSource

}