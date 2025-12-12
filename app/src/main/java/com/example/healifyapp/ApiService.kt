package com.example.healifyapp

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {

    @GET("hello")
    suspend fun getHello(): String

    @POST("send")
    suspend fun postData(@Body body: Map<String, String>): String
}
