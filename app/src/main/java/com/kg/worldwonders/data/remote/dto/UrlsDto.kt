package com.kg.worldwonders.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UrlsDto(
    val detail: String? = null,
    val edit: String? = null,
    val provider: String? = null,
)