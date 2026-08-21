package com.kg.worldwonders.data.remote.dto

import com.kg.worldwonders.domain.model.PlayerUrls
import com.kg.worldwonders.domain.model.Webcam
import com.kg.worldwonders.domain.model.WebcamCategory
import kotlinx.serialization.Serializable
import kotlin.time.Instant

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

fun WebcamDto.toDomain() = Webcam(
    id = webcamId,
    title = title ?: "Unknown location",
    player = player?.let { PlayerUrls(it.day, it.month, it.year, it.lifetime) },
    previewImageUrl = images?.current?.preview,
    city = location?.city,
    country = location?.country,
    countryCode = location?.countryCode,
    latitude = location?.latitude,
    longitude = location?.longitude,
    categories = categories.map { WebcamCategory(it.id, it.name ?: it.id) },
    viewCount = viewCount ?: 0,
    lastUpdatedOn = lastUpdatedOn?.let { runCatching { Instant.parse(it) }.getOrNull() },
    isActive = status == "active",
)

@Serializable
data class CategoryDto(
    val id: String,
    val name: String? = null
)

fun CategoryDto.toDomain() : WebcamCategory
{
    return WebcamCategory(
        id = id,
        name = name ?: id
    )
}