package com.jkirwa.penguinpay.feature_send_money.domain.repository

import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.api.ExchangeRateApiService
import com.jkirwa.penguinpay.feature_send_money.data.repository.ExchangeRatesRepository
import com.jkirwa.penguinpay.feature_send_money.domain.utils.Constants
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Result
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

internal class ExchangeRatesRepositoryImpl(
    private val exchangeRateApiService: ExchangeRateApiService,
    private val isDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExchangeRatesRepository {
    override suspend fun fetchCurrentExchangeRates(): Result<Boolean> {
        return withContext(isDispatcher) {
            try {
                Result.Loading
                val appId = Constants.APIKEY
                val result = exchangeRateApiService.getCurrentExchangeRate(appId = appId)
                if (result.isSuccessful) {
                    result.body()?.apply {
                       Timber.d("Rates--"+rates?.kes)
                    }
                    Result.Success(true)
                } else {
                    Result.Success(false)
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (e: IOException) {
                Result.Error(Exception("Error Occurred"))
                e.printStackTrace()
                Timber.e(e)
            }
            Result.Success(false)
        }
    }


}
