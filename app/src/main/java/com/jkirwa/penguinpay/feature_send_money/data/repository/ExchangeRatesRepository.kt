package com.jkirwa.penguinpay.feature_send_money.data.repository

import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Result

interface ExchangeRatesRepository {
    suspend fun fetchCurrentExchangeRates(): Result<Boolean>
}
