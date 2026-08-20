package com.kg.worldwonders.data.local.geo

import kotlinx.serialization.Serializable

@Serializable
data class GeoCodeDto(
    val code: String,
    val name: String
)