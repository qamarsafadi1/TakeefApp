package com.selsela.takeefapp.data.address

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApi {

    @GET("https://maps.googleapis.com/maps/api/place/findplacefromtext/json")
    suspend fun getPredictions(
        @Query("key") key: String = "AIzaSyA8AitrTSHiTtbvtlDbmrwqZH1HRq0LxLQ",
        @Query("inputtype") types: String = "textquery",
        @Query("fields") fields: String = "formatted_address,name,geometry",
        @Query("input") input: String
    ): Response<GooglePredictionsResponse>

}