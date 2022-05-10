package com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkirwa.penguinpay.feature_send_money.data.data_source.local.Country
import com.jkirwa.penguinpay.feature_send_money.data.data_source.local.countries
import com.jkirwa.penguinpay.feature_send_money.data.repository.ExchangeRatesRepository
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class SendMoneyViewModel(private val exchangeRatesRepository: ExchangeRatesRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(ExchangeRatesState())
    val state: StateFlow<ExchangeRatesState> = _state

    private var getExchangeJob: Job? = null

    init {
        if (countries.isNotEmpty()) {
            val defaultCountry = countries[0]
            _state.value = state.value.copy(
                selectedCountry = defaultCountry
            )
        }
    }


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

                    if (result.data.isSuccessful) {
                        _state.value = state.value.copy(
                            rates = result.data.body()?.rates,
                            selectedRate = state.value.rates?.get(state.value.selectedCountry?.currencyCode)
                        )
                    }
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

    fun updateSelectedCountry(country: Country) {
        _state.value = state.value.copy(
            selectedCountry = country,
            selectedRate = state.value.rates?.get(country.currencyCode)
        )
    }

    fun updateAmount(binaryAmount: String, amount: Int) {
        _state.value = state.value.copy(
            amountBinary = binaryAmount,
            amountToTransfer = amount * state.value.rates?.get(state.value.selectedCountry?.currencyCode)!!
        )
    }


}

data class ExchangeRatesState(
    val rates: Map<String, Double>? = emptyMap(),
    var selectedCountry: Country? = null,
    var selectedRate: Double? = 0.0,
    var amountBinary: String = "",
    var enteredFirstName: String = "",
    var enteredLastName: String = "",
    var enteredPhoneNumber: String = "",
    var amountToTransfer: Double = 0.0,
    val isSuccessFetchingExchangeRates: Boolean = false,
    val isFetchingExchangeRates: Boolean = false,
    val isErrorFetchingExchangeRates: Boolean = false,
    val errorMessage: String? = null
)
