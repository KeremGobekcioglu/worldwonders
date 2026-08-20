package com.kg.worldwonders.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WindyApi
{
    /**
     * curl.exe -X
     * GET "https://api.windy.com/webcams/api/v3/webcams?lang=en&limit=10&offset=0&categories=forest,lake,landscape&categoryOperation=or&include=categories,images,location,player,urls"
     */
    @GET("/webcams/api/v3/webcams")
    suspend fun getWebcams(
        @Query("lang") lang: String = "en",
        /**
         * default value 10. range 0 to 50.
         */
        @Query("limit") limit: Int = 20,
        /**
         * used for pagination. free tier 1000.
         */
        @Query("offset") offset : Int = 0, // used for pagination.
        /**
         * this can be used to fetch popular cameras. popularity,createdOn
         */
        @Query("sortKey") sortKey: String? = null, // this can be used to fetch popular cameras. popularity,createdOn
        /**
         * asc,desc
         */
        @Query("sortDirection") sortDirection: String? = null, // asc,desc
        /**
         * Retrieves a list of webcams within the specified geographic bounding box.
         * Use the following format for the bounding box coordinates:
         * Format: {north_latitude},{east_longitude},{south_latitude},{west_longitude}
         * Example: 45.90,6.11,40.50,3.62
         * Latitude range: -90 to 90
         * Longitude range: -180 to 180
         */
        @Query("bbox") bbox: String? = null,
        /**
         * Retrieves a list of webcams within a specified radius (in kilometers) around a given location.
         * Use the following format for the nearby coordinates and radius:
         * Format: {latitude},{longitude},{radius}
         * Example: 446.54,7.98,5
         * Latitude range: -90 to 90
         * Longitude range: -180 to 180
         * Maximal radius: 250km
         */
        @Query("nearby") nearby: String? = null,
        /**
         * airport,beach,building,city,coast,forest,indoor,lake,landscape,meteo,mountain,observatory,port,river,sportArea,square,traffic,village
         * */
        @Query("categories") categories: String? = null,
        /**
         * Specify the operator to be used for categories (either and or or)..
         * The categories parameter is required for this operation.
         * The default value is and.
         */
        @Query("categoryOperation") categoryOperation: String? = null,
        /**
         * /webcams/api/v3/continents returns continents but we can write them by hand.
         * Maximum number of continents: 2
         * Available values : AF, AN, AS, EU, NA, OC, SA
         */
        @Query("continents") continents: String? = null,
        /**
         * we ll get them from some enum class. will prepare it.
         */
        @Query("countries") countries: String? = null,
        /**
         * we ll get them from some enum class. will prepare it.
         */
        @Query("regions") regions: String? = null,
        /**
         * will never use cities.
         */
        @Query("cities") cities: String? = null,
        /**
         * Returns a list of webcams based on webcam ids
         * If webcamIds filter is set, no other filters are applied
         *
         * Maximum number of webcams: 50
         */
        @Query("webcamIds") webcamIds: String? = null,
        @Query("include") include: String? = "images,location,categories,player,urls"
    ) : String

    @GET("/webcams/api/v3/webcams/{webcamId}")
    suspend fun getWebcam(
        @Path("webcamId") webcamId : Long,
        @Query("lang") lang: String = "en",
        @Query("include") include: String? = "images,location,categories,player,urls"
    ) : String

    @GET("/webcams/api/v3/categories")
    suspend fun getCategories(
        @Query("lang") lang: String = "en"
    ) : String
}