package com.kg.worldwonders.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val city: String? = null,
    val region: String? = null,
    @SerialName("region_code") val regionCode: String? = null,
    val country: String? = null,
    @SerialName("country_code") val countryCode: String? = null,
    val continent: String? = null,
    @SerialName("continent_code") val continentCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
)