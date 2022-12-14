package com.example.banklist.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.example.banklist.models.BankModel
import com.example.banklist.network.ApiServiceRepository
import com.example.banklist.network.BankApiService
import com.example.banklist.utils.Resource
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BankListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiServiceRepository: ApiServiceRepository
) :
    ViewModel() {

    private lateinit var analytics: FirebaseAnalytics
    private val _shownBankList = MutableLiveData<List<BankModel>>()
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val _errorState = MediatorLiveData<Boolean>()
    val errorState: LiveData<Boolean> = _errorState
    private val _errorText = MutableLiveData("")
    val errorText: LiveData<String> = _errorText
    private val _isApiFailed = MutableLiveData(false)
    private val _networkState = MutableLiveData(true)
    private val _loadingState = MutableLiveData(false)
    val loadingState : LiveData<Boolean> = _loadingState

    private var _apiCallJob: Job? = null

    fun initAnalytics(){
        analytics = Firebase.analytics
    }

    fun sendFirebaseEvent(bankModel: BankModel){
        val bundle = Bundle()
        bundle.putInt("bankId",bankModel.bankId)
        bundle.putString("city",bankModel.city)
        bundle.putString("district",bankModel.district)
        bundle.putString("bankBranch",bankModel.bankBranch)
        bundle.putString("bankType",bankModel.bankType)
        bundle.putString("bankCode",bankModel.bankCode)
        bundle.putString("addressName",bankModel.addressName)
        bundle.putString("address",bankModel.address)
        bundle.putString("postCode",bankModel.postCode)
        bundle.putString("on_off_line",bankModel.on_off_line)
        bundle.putString("on_off_site",bankModel.on_off_site)
        bundle.putString("regionalCoordinator",bankModel.regionalCoordinator)
        bundle.putString("closestATM",bankModel.closestATM)
        analytics.logEvent("SELECTED_BANK",bundle)
    }

    init {
        _errorState.addSource(_networkState) { errorUpdate() }
        _errorState.addSource(_isApiFailed) { errorUpdate() }
    }

    private fun errorUpdate() {
        _errorState.value = _networkState.value == false || _isApiFailed.value == true

    }

    val shownBankList: LiveData<List<BankModel>> = currentQuery.switchMap { query ->
        if (query.isNullOrEmpty()) {
            _shownBankList
        } else {
            val tempList = _shownBankList.value?.filter {
                it.city.startsWith(query, true)
            }
            liveData {
                tempList?.let { emit(it) }
            }
        }
    }

    fun searchItems(query: String) {
        currentQuery.value = query
    }
    
    fun loadData(){
        _apiCallJob?.cancel()
        _apiCallJob = viewModelScope.launch {
            apiServiceRepository.getBankList().collect{
                res ->
                _loadingState.value = res is Resource.Loading
                res.onSuccess {
                    _shownBankList.value = res.data ?: ArrayList()
                    _isApiFailed.value = false
                }
                res.onError { throwable, data ->
                    _errorText.postValue("Banka servisine ula????lamad??.")
                    _isApiFailed.postValue(true)
                }
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
                _errorText.postValue("Internet ba??lant??s?? yok.")
            }

            override fun onUnavailable() {
                _networkState.postValue(false)
                _errorText.postValue("Internet ba??lant??s?? yok.")
            }
        })
    }

    companion object {
        private val DEFAULT_QUERY = ""
    }
}