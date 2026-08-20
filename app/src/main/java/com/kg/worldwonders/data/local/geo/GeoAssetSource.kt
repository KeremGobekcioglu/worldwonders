package com.kg.worldwonders.data.local.geo

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Loads [GeoCodeDto] lists (continents, countries) from JSON files bundled
 * under `assets/geo/`, parsing each file at most once and caching the result
 * for the lifetime of the singleton.
 *
 * Concurrent calls for the same file are serialized via [mutex] so the
 * underlying JSON is decoded only once even when requested from multiple
 * coroutines at the same time.
 */
@Singleton
class GeoAssetSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
)
{
    /**
     * this cache persists because this class is a singleton.
     */
    private val cache = mutableMapOf<String, List<GeoCodeDto>>()

    /**
     * this mutex prevents two corotuine parsing same file concurrently.
     * getOrPut on a plain mutableMapOf İS NOT thread safe.
     */
    private val mutex = Mutex()
    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun load(filename: String) : List<GeoCodeDto> {
        return mutex.withLock {
            cache.getOrPut(filename){
                withContext(Dispatchers.IO)
                {
                    context.assets.open("geo/$filename").use {
                        stream ->
                            json.decodeFromStream<List<GeoCodeDto>>(stream)
                    }
                }
            }
        }
    }


    suspend fun continents(): List<GeoCodeDto> = load("continents.json")
    suspend fun countries(): List<GeoCodeDto> = load("countries.json")
}