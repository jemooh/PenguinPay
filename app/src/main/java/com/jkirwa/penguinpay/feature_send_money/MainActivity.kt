package com.jkirwa.penguinpay.feature_send_money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jkirwa.penguinpay.R
import com.jkirwa.penguinpay.feature_send_money.presentation.sendmoney.SendMoneyScreen
import com.jkirwa.penguinpay.feature_send_money.presentation.theme.PenguinPayAppTheme
import com.jkirwa.penguinpay.feature_send_money.presentation.theme.TopBar
import com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel.SendMoneyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val sendViewModel: SendMoneyViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendViewModel.fetchExchangeRate()
        setContent {
            MainScreen()
        }
    }


    @Composable
    fun MainScreen() {
        PenguinPayAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                Scaffold(
                    topBar = { TopBar() }
                ) {
                    SendMoneyScreen()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }
}