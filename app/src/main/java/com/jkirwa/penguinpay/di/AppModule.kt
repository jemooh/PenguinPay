package com.jkirwa.penguinpay.di

import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.api.ExchangeRateApiService
import com.jkirwa.penguinpay.feature_send_money.data.repository.ExchangeRatesRepository
import com.jkirwa.penguinpay.feature_send_money.domain.repository.ExchangeRatesRepositoryImpl
import com.jkirwa.penguinpay.feature_send_money.domain.utils.Constants
import com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel.SendMoneyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

const val baseUrl: String = Constants.BASE_URL
const val apiKey: String = Constants.APIKEY

val appModule = module {
    single { createNetworkClient(baseUrl, apiKey) }
    single { (get() as? Retrofit)?.create(ExchangeRateApiService::class.java) }

    factory<ExchangeRatesRepository> {
        ExchangeRatesRepositoryImpl(
            exchangeRateApiService = get()
        )
    }

    viewModel {
        SendMoneyViewModel(exchangeRatesRepository = get())
    }
}
