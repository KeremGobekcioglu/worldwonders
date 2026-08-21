package com.kg.worldwonders.data.repository

import com.kg.worldwonders.common.ApiResult
import com.kg.worldwonders.data.remote.WindyApi
import com.kg.worldwonders.data.remote.dto.toDomain
import com.kg.worldwonders.domain.model.CategoryOperation
import com.kg.worldwonders.domain.model.SortDirection
import com.kg.worldwonders.domain.model.SortKey
import com.kg.worldwonders.domain.model.Webcam
import com.kg.worldwonders.domain.model.WebcamCategory
import com.kg.worldwonders.domain.repository.WebcamRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebcamRepositoryImpl @Inject constructor(
    private val api: WindyApi
): WebcamRepository
{
    override suspend fun getSingleWebcam(
        id: Long,
        lang: String
    ): ApiResult<Webcam> {
        return try {
            val response = api.getWebcam(
                id, lang
            )
            ApiResult.Success(response.toDomain())
        }
        catch (e : Exception)
        {
            ApiResult.Error(e.message ?: "getSingleWebcam throw", e)
        }
    }

    override suspend fun getListOfWebcams(
        lang: String,
        limit: Int,
        offset: Int,
        categoryIds: List<String>,
        categoryOperation: CategoryOperation,
        sortKey: SortKey?,
        sortDirection: SortDirection?,
        countryCodes: List<String>,
        continentCodes: List<String>,
    ): ApiResult<List<Webcam>> {
        return try {
            val response = api.getWebcams(
                lang = lang,
                limit = limit.coerceIn(1,50), // free limit offers 50 at max.
                offset = offset.coerceIn(0,1000), // 1000 is ceiling
                sortKey = sortKey?.value,
                sortDirection = sortDirection?.value,
                bbox = null,
                categories = if(categoryIds.isNotEmpty()) {
                    categoryIds.joinToString(",")
                } else null,
                categoryOperation = if(categoryIds.isNotEmpty()) {
                    categoryOperation.value
                } else null,
                countries = countryCodes.takeIf { it.isNotEmpty() }?.joinToString(","),
                continents = continentCodes.takeIf { it.isNotEmpty() }?.joinToString(","),
            )
            ApiResult.Success(
                response.webcams.map {
                    it.toDomain()
                }
            )
        }
        catch (e: Exception)
        {
            ApiResult.Error(e.message ?: "getListOfWebcams throw", e)
        }
    }

    override suspend fun getWebcamsNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Int,
        limit: Int,
        lang: String
    ): ApiResult<List<Webcam>> {
        return try {
            val response = api.getWebcams(
                lang = lang,
                limit = limit.coerceIn(1, 50),
                // 250 km is ceiling.
                nearby = "$latitude,$longitude,${radiusKm.coerceIn(1, 250)}",
            )
            ApiResult.Success(response.webcams.map { it.toDomain() })
        }
        catch (e: Exception)
        {
            ApiResult.Error(e.message ?: "getWebcamsNearby throw", e)
        }
    }

    override suspend fun getCategories(
        lang: String
    ): ApiResult<List<WebcamCategory>> {
        return try {
            val response = api.getCategories(
                lang = lang
            )
            ApiResult.Success(response.map { it.toDomain() })
        }
        catch (e : Exception)
        {
            ApiResult.Error(e.message ?: "getCategories throw", e)
        }
    }

}