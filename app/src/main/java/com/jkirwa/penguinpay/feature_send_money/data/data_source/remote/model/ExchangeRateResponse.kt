package com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @field:SerializedName("rates")
    val rates: Rates? = null,

)

data class Rates(

    @field:SerializedName("KES")
    val kes: String? = null,

    @field:SerializedName("NGN")
    val ngn: String? = null,

    @field:SerializedName("TZS")
    val tzs: String? = null,

    @field:SerializedName("UGX")
    val ugx: String? = null
)


