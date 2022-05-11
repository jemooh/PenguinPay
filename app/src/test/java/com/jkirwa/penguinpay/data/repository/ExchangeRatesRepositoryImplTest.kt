package com.jkirwa.penguinpay.data.repository

import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.jkirwa.penguinpay.data.dispacher.MockExchangeRateRequestDispatcher
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.api.ExchangeRateApiService
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.ExchangeRateResponse
import com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model.Result
import com.jkirwa.penguinpay.feature_send_money.data.repository.ExchangeRatesRepository
import com.jkirwa.penguinpay.feature_send_money.domain.repository.ExchangeRatesRepositoryImpl
import com.jkirwa.penguinpay.feature_send_money.domain.utils.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ExchangeRatesRepositoryImplTest : Spek({

    lateinit var mockWebServer: MockWebServer
    lateinit var okHttpClient: OkHttpClient
    lateinit var loggingInterceptor: HttpLoggingInterceptor
    var exchangeRateAPI: ExchangeRateApiService

    lateinit var exchangeRatesRepository: ExchangeRatesRepository

     var result: Result<Response<ExchangeRateResponse>>? =null

    fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    Feature("Fetching Latest Exchange Rates") {

        beforeEachScenario {
            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = MockExchangeRateRequestDispatcher()
            mockWebServer.start()
            loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClient = buildOkhttpClient(loggingInterceptor)

            val gson = GsonBuilder()
                .serializeNulls()
                .create()

            exchangeRateAPI = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ExchangeRateApiService::class.java)

            exchangeRatesRepository =
                ExchangeRatesRepositoryImpl(exchangeRateApiService = exchangeRateAPI)
        }

        afterEachScenario {
            mockWebServer.shutdown()
        }

        Scenario("When the valid app key is passed we expect to fetch latest Current Exchange Rates") {

            Given("Make the request to the open exchange api") {
                runBlocking {
                    result = exchangeRatesRepository.fetchCurrentExchangeRates()
                }
            }

            When("We assert that the result we get is an instance of Result") {
                Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
            }

            Then("We check the value of KES to check if we get the correct value") {
                Truth.assertThat((result as Result.Success).data.body()?.rates?.get("KES"))
                    .isEqualTo(116.06)
            }

        }
    }
})