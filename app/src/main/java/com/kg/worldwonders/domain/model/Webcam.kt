package com.kg.worldwonders.domain.model

import kotlin.time.Instant
data class Webcam(
    val id: Long,
    val title: String,
    val player: PlayerUrls?,
    val previewImageUrl: String?,
    val city: String?,
    val country: String?,
    val countryCode: String?,
    val latitude: Double?,
    val longitude: Double?,
    val categories: List<WebcamCategory>,
    val viewCount: Long,
    val lastUpdatedOn: Instant?,
    val isActive: Boolean,   // from status == "active"
)
data class WebcamCategory(
    val id: String,     // "meteo" — send this to the API
    val name: String,   // "Weather" — show this to the user
)

data class PlayerUrls(
    val day: String?,
    val month: String?,
    val year: String?,
    val lifetime: String?,
)