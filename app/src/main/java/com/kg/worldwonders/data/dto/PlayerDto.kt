package com.kg.worldwonders.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val day: String? = null,
    val month: String? = null,
    val year: String? = null,
    val lifetime: String? = null,
)