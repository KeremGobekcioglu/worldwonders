package com.kg.worldwonders.data.local.geo

import kotlinx.serialization.Serializable

/**
 * A single geo entry (continent or country) loaded from the local asset JSON files.
 *
 * @property code short identifier for the entry (e.g. an ISO country code).
 * @property name human-readable display name of the entry.
 */
@Serializable
data class GeoCodeDto(
    val code: String,
    val name: String
)