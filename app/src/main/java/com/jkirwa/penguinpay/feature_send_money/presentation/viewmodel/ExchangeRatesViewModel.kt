package com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Rates
import com.jkirwa.penguinpay.feature_send_money.data.repository.ExchangeRatesRepository
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(private val exchangeRatesRepository: ExchangeRatesRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(ExchangeRatesState())
    val state: StateFlow<ExchangeRatesState> = _state

    private var getExchangeJob: Job? = null


    fun fetchExchangeRate() {
        getExchangeJob?.cancel()
        getExchangeJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = exchangeRatesRepository.fetchCurrentExchangeRates()) {
                is Result.Loading -> {
                    _state.value = state.value.copy(
                        isFetchingExchangeRates = true
                    )
                }
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isSuccessFetchingExchangeRates = true
                    )
                }
                is Result.Error -> {
                    _state.value = state.value.copy(
                        isErrorFetchingExchangeRates = true,
                        errorMessage = result.exception.message.toString()
                    )
                }
            }
        }
    }


}

data class ExchangeRatesState(
    val rates: Rates? = null,
    val isSuccessFetchingExchangeRates: Boolean = false,
    val isFetchingExchangeRates: Boolean = false,
    val isErrorFetchingExchangeRates: Boolean = false,
    val errorMessage: String? = null
)
