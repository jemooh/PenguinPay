package com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.api

import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.*

interface ExchangeRateApiService {

    @GET("latest.json")
    suspend fun getCurrentExchangeRate(
        @Query("app_id") appId: String
    ): Response<ExchangeRateResponse>

}
