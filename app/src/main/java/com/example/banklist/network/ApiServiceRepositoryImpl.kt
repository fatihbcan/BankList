package com.example.banklist.network

import com.example.banklist.models.BankModel
import com.example.banklist.utils.Resource
import com.example.banklist.utils.dispatcher_provider.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class ApiServiceRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val bankApiService: BankApiService
) : ApiServiceRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getBankList(): Flow<Resource<List<BankModel>>> =
        callbackFlow {
            trySend(Resource.Loading())
            try {
                val response = bankApiService.getAllBanks()
                trySend(Resource.Success(response))
                close()
            } catch (e: Exception) {
                trySend(
                    Resource.Error(Exception("exception"))
                )
                close()
            }

        }.flowOn(dispatcherProvider.mainImmediate)
}