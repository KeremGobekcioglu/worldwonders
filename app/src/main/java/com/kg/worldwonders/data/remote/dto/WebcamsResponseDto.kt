package com.kg.worldwonders.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WebcamsResponseDto(
    val total: Int = 0,
    val webcams: List<WebcamDto> = emptyList()
)