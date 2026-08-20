package com.kg.worldwonders.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImagesDto(
    val current: ImageSetDto? = null,
    val daylight: ImageSetDto? = null,
    val sizes: SizesDto? = null,
)
@Serializable
data class ImageSetDto(
    val icon: String? = null,
    val thumbnail: String? = null,
    val preview: String? = null,
)

@Serializable
data class SizesDto(
    val icon: DimensionsDto? = null,
    val thumbnail: DimensionsDto? = null,
    val preview: DimensionsDto? = null,
)

@Serializable
data class DimensionsDto(
    val width: Int? = null,
    val height: Int? = null,
)
