package me.basehub.common.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T? = null,
    val meta: PaginationMeta? = null
)

@Serializable
data class PaginationMeta(
    val currentPage: Int? = null,
    val totalPages: Int? = null,
    val totalItems: Int? = null,
    val itemsPerPage: Int? = null
)