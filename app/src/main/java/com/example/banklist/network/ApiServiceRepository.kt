package com.example.banklist.network

import com.example.banklist.models.BankModel
import com.example.banklist.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ApiServiceRepository {

    fun getBankList(): Flow<Resource<List<BankModel>>>
}