package com.example.banklist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banklist.network.BankApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class BankListViewModel @Inject constructor(private val bankApiService: BankApiService) : ViewModel() {

    fun deneme(){
        viewModelScope.launch(IO) {
            bankApiService.getAllBanks()
        }
    }
}