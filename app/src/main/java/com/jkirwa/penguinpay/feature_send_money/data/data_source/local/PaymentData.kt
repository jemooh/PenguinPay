package com.jkirwa.penguinpay.feature_send_money.data.data_source.local

data class PaymentData(
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var amount: String,
    var country: String
)