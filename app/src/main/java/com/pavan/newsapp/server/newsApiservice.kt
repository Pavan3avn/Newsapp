package com.pavan.newsapp.server

import com.pavan.newsapp.model.Newsresponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface newsApiservice {

    @GET("/v2/top-headlines")
    suspend fun getallarticles(
        @Query("country") country:String,
        @Query("category") category:String,
        @Query("apiKey") apiKey: String
    ):Response<Newsresponse>
}