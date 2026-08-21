package com.kg.worldwonders.domain.repository

import com.kg.worldwonders.common.ApiResult
import com.kg.worldwonders.domain.model.CategoryOperation
import com.kg.worldwonders.domain.model.SortDirection
import com.kg.worldwonders.domain.model.SortKey
import com.kg.worldwonders.domain.model.Webcam
import com.kg.worldwonders.domain.model.WebcamCategory

interface WebcamRepository {

    suspend fun getSingleWebcam(
        id: Long,
        lang: String = "en"
    ) : ApiResult<Webcam>

    suspend fun getListOfWebcams(
        lang: String = "en",
        limit: Int = 20,
        offset: Int = 0,
        categoryIds: List<String> = emptyList(),
        categoryOperation: CategoryOperation = CategoryOperation.OR,
        sortKey: SortKey? = null,
        sortDirection: SortDirection? = null,
        countryCodes: List<String> = emptyList(),
        continentCodes: List<String> = emptyList(),
    ) : ApiResult<List<Webcam>>

    suspend fun getWebcamsNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Int = 50,
        limit: Int = 20,
        lang: String = "en"
    ) : ApiResult<List<Webcam>>

    suspend fun getCategories(
        lang: String = "en"
    ) : ApiResult<List<WebcamCategory>>
}