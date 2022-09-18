package com.example.banklist.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.banklist.models.BankModel
import com.example.banklist.network.BankApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankListViewModel @Inject constructor(private val bankApiService: BankApiService) :
    ViewModel() {

    private val _shownBankList = MutableLiveData<List<BankModel>>()

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val shownBankList: LiveData<List<BankModel>> = currentQuery.switchMap { query ->
        Log.d("Fatih", "query updated")

        if (query.isNullOrEmpty()) {
            _shownBankList
        } else {
            val tempList = _shownBankList.value?.filter {
                it.city.startsWith(query, true)}
            liveData {
                tempList?.let { emit(it) }
            }
        }
    }

    fun searchItems(query: String) {
        Log.d("Fatih", "query : " + query)
        currentQuery.value = query
    }

    fun loadData() {
        viewModelScope.launch {
            _shownBankList.value = bankApiService.getAllBanks()
        }
    }

    companion object {
        private val DEFAULT_QUERY = ""
    }
}