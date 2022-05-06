package com.jkirwa.penguinpay.feature_send_money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jkirwa.penguinpay.R
import com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel.ExchangeRatesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val exchangeRatesViewModel: ExchangeRatesViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exchangeRatesViewModel.fetchExchangeRate()
    }

}