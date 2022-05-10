package com.jkirwa.penguinpay.feature_send_money.data.repository

import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.ExchangeRateResponse
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Result
import retrofit2.Response

interface ExchangeRatesRepository {
    suspend fun fetchCurrentExchangeRates(): Result<Response<ExchangeRateResponse>>?
}
