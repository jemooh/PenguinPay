package com.jkirwa.penguinpay.feature_send_money.domain.repository

import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.api.ExchangeRateApiService
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.ExchangeRateResponse
import com.jkirwa.penguinpay.feature_send_money.data.repository.ExchangeRatesRepository
import com.jkirwa.penguinpay.feature_send_money.domain.utils.Constants
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Result
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

internal class ExchangeRatesRepositoryImpl(
    private val exchangeRateApiService: ExchangeRateApiService,
    private val isDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExchangeRatesRepository {
    override suspend fun fetchCurrentExchangeRates(): Result<Response<ExchangeRateResponse>> {
        return withContext(isDispatcher) {
            try {
                Result.Loading
                val appId = Constants.APIKEY
                val result = exchangeRateApiService.getCurrentExchangeRate(appId = appId)
                Result.Success(result)
            } catch (e: IOException) {
                e.printStackTrace()
                Result.Error(Exception("Error Occurred fetching Exchange Rates, Check your Internet connection"))
            }
        }
    }


}
