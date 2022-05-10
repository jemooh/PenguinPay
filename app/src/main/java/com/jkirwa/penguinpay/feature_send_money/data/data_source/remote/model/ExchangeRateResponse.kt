package com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @field:SerializedName("rates")
    val rates: Map<String, Double>

)


