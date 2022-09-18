package com.example.banklist.network

import com.example.banklist.models.BankModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BankApiService {

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }

    @GET("fatiha380/mockjson/main/bankdata")
    suspend fun getAllBanks(): List<BankModel>
}