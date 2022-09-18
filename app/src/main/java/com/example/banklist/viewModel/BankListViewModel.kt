package com.example.banklist.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.banklist.models.BankModel
import com.example.banklist.network.BankApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.net.NetworkRequest

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext


@HiltViewModel
class BankListViewModel @Inject constructor(private val bankApiService: BankApiService, @ApplicationContext private val context: Context) :
    ViewModel() {

    private val _shownBankList = MutableLiveData<List<BankModel>>()
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val _errorState = MediatorLiveData<Boolean>()
    val errorState : LiveData<Boolean> = _errorState
    private val _errorText = MutableLiveData("")
    val errorText : LiveData<String> = _errorText
    private val _isApiFailed = MutableLiveData(false)
    private val _networkState = MutableLiveData(true)

    init {
        _errorState.addSource(_networkState) {errorUpdate()}
        _errorState.addSource(_isApiFailed) {errorUpdate()}
    }

    private fun errorUpdate(){
        _errorState.value = _networkState.value == false || _isApiFailed.value == true
        Log.d("Fatih","error update worked.")

    }

    val shownBankList: LiveData<List<BankModel>> = currentQuery.switchMap { query ->
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
        currentQuery.value = query
    }

    fun loadData() {
        viewModelScope.launch {
            try{
                _shownBankList.value = bankApiService.getAllBanks()
                _isApiFailed.value = false
            } catch (e : Exception){
                Log.e("Fatih", "exception : $e")
                _errorText.postValue("Banka servisine ulaşılamadı.")
                _isApiFailed.postValue(true)
            }
        }
    }

    fun observeConnection() {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        manager.registerNetworkCallback(networkRequest, object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                _networkState.postValue(true)
                loadData()
            }

            override fun onLost(network: Network) {
                _networkState.postValue(false)
                _errorText.postValue("Internet bağlantısı yok.")
                Log.d("Fatih","onlost conneciton")
            }

            override fun onUnavailable() {
                _networkState.postValue(false)
                _errorText.postValue("Internet bağlantısı yok.")
                Log.d("Fatih","onUnavailable conneciton")

            }
        })
    }

    companion object {
        private val DEFAULT_QUERY = ""
    }
}