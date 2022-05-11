package com.jkirwa.penguinpay.feature_send_money.presentation.sendmoney

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel.SendMoneyViewModel
import org.koin.androidx.compose.getViewModel


@Preview
@Composable
fun AmountViewCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(bottom = 8.dp)
                .padding(4.dp)
        ) {
            AmountConversionStateView()
            Spacer(Modifier.size(15.dp))
            ConvertedAmount()
        }
    }
}

@Composable
fun AmountConversionStateView() {
    val sendViewModel = getViewModel<SendMoneyViewModel>()
    val uiState = sendViewModel.state.collectAsState().value

    when {
        uiState.isFetchingExchangeRates -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.isErrorFetchingExchangeRates -> {
            ErrorText(uiState.errorMessage.toString())
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Open Exchange Rates:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1 BN = ${uiState.selectedCountry?.currencyCode} ${uiState.rates?.get(uiState.selectedCountry?.currencyCode)} ",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "This rate is an estimate based on the current rate",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

@Composable
fun ConvertedAmount() {
    val sendViewModel = getViewModel<SendMoneyViewModel>()
    val uiState = sendViewModel.state.collectAsState().value
    val binaria: String = if (uiState.amountBinary.isEmpty()) {
        "0"
    } else {
        uiState.amountToTransfer.toString()
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Amount to Receive",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "BN $binaria",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}



