package com.jkirwa.penguinpay.feature_send_money.presentation.sendmoney

import android.annotation.SuppressLint
import androidx.cardview.widget.CardView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius.Companion.Zero
import androidx.compose.ui.geometry.Offset.Companion.Zero
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.togitech.ccp.data.utils.getDefaultLangCode
import com.togitech.ccp.data.utils.getDefaultPhoneCode
import com.jkirwa.penguinpay.R
import com.jkirwa.penguinpay.feature_send_money.data.data_source.local.countries
import com.jkirwa.penguinpay.feature_send_money.domain.utils.PrefixTransformation
import com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel.ExchangeRatesViewModel
import com.togitech.ccp.component.TogiCountryCodePicker
import com.togitech.ccp.data.utils.checkPhoneNumber
import com.togitech.ccp.data.utils.getLibCountries
import org.koin.androidx.compose.getViewModel
import java.lang.NumberFormatException


@Preview
@Composable
fun AmountViewCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            AmountConversionStateView()
            Spacer(Modifier.size(15.dp))
            ConvertedAmount()
        }
    }
}

@Composable
fun AmountConversionStateView() {
    val ratesViewModel = getViewModel<ExchangeRatesViewModel>()
    val uiState = ratesViewModel.state.collectAsState().value

    if (!uiState.isSuccessFetchingExchangeRates) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
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

@Composable
fun ConvertedAmount() {
    val ratesViewModel = getViewModel<ExchangeRatesViewModel>()
    val uiState = ratesViewModel.state.collectAsState().value
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



