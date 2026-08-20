package com.kg.worldwonders.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WebcamDto(
    val webcamId: Long,
    val title: String? = null,
    val viewCount: Long? = null,
    val status: String? = null,
    val lastUpdatedOn: String? = null,
    val categories: List<CategoryDto> = emptyList(),
    val images: ImagesDto? = null,
    val location: LocationDto? = null,
    val player: PlayerDto? = null,
    val urls: UrlsDto? = null,
)


@Serializable
data class CategoryDto(
    val id: String,
    val name: String? = null
)