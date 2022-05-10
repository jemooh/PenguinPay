package com.jkirwa.penguinpay.feature_send_money.data.data_source.local
import com.jkirwa.penguinpay.R

data class Country(
    val countryName: String,
    val currencyCode: String,
    val countryCode: String,
    val countryFlag: Int,
    val phoneLength: Int
)

val countries = listOf(
    Country(
        countryName = "Kenya",
        currencyCode = "KES",
        countryCode = "+254",
        countryFlag = R.drawable.ic_flag_ke,
        phoneLength = 9
    ), Country(
        countryName = "Nigeria",
        currencyCode = "NGN",
        countryCode = "+234",
        countryFlag = R.drawable.ic_flag_ng,
        phoneLength = 7
    ),
    Country(
        countryName = "Tanzania",
        currencyCode = "TZS",
        countryCode = "+255",
        countryFlag = R.drawable.ic_flag_tz,
        phoneLength = 9
    ),
    Country(
        countryName = "Uganda",
        currencyCode = "UGX",
        countryCode = "+256",
        countryFlag = R.drawable.ic_flag_ug,
        phoneLength = 7
    )
)