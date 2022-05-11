package com.jkirwa.penguinpay.feature_send_money.presentation.sendmoney

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
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
import com.jkirwa.penguinpay.R
import com.jkirwa.penguinpay.feature_send_money.data.data_source.local.PaymentData
import com.jkirwa.penguinpay.feature_send_money.data.data_source.local.countries
import com.jkirwa.penguinpay.feature_send_money.domain.utils.PrefixTransformation
import com.jkirwa.penguinpay.feature_send_money.domain.utils.Util
import com.jkirwa.penguinpay.feature_send_money.domain.utils.Util.isBinaryNumber
import com.jkirwa.penguinpay.feature_send_money.presentation.viewmodel.SendMoneyViewModel
import org.koin.androidx.compose.getViewModel
import java.lang.NumberFormatException

@Preview
@Composable
fun SendMoneyScreen() {
    val sendViewModel = getViewModel<SendMoneyViewModel>()
    val uiState = sendViewModel.state.collectAsState().value
    val context = LocalContext.current
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {

        var showLastNameCheckIcon by remember { mutableStateOf(false) }
        var showFirstNameCheckIcon by remember { mutableStateOf(false) }
        var showPhoneNumberCheckIcon by remember { mutableStateOf(false) }
        var showAmountCheckIcon by remember { mutableStateOf(false) }
        val textStatePhoneNumber = remember { mutableStateOf("") }
        val textStateFirstName = remember { mutableStateOf("") }
        val textStateLastName = remember { mutableStateOf("") }

        var isErrorFirstName by rememberSaveable { mutableStateOf(false) }
        var isErrorInvalidFirstName by rememberSaveable { mutableStateOf(false) }
        var isErrorLastName by rememberSaveable { mutableStateOf(false) }
        var isErrorInvalidLastName by rememberSaveable { mutableStateOf(false) }

        if (uiState.isSuccessPostPayment) {
            val paymentData = uiState.selectedCountry?.countryName?.let {
                PaymentData(
                    uiState.enteredFirstName,
                    uiState.enteredLastName,
                    uiState.selectedCountry?.countryCode + textStatePhoneNumber.value,
                    uiState.amountBinary,
                    it
                )
            }
            if (paymentData != null) {
                SuccessDialog(paymentData = paymentData)
            }
        }


        Text(
            text = stringResource(id = R.string.subtitle_fill_details),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(bottom = 16.dp)
                .align(CenterHorizontally)
        )

        Column() {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(end = 16.dp)
                    .padding(start = 16.dp),
                value = textStateFirstName.value,
                isError = isErrorFirstName,
                onValueChange = { onQueryChanged ->
                    isErrorFirstName = false
                    isErrorInvalidFirstName = false
                    textStateFirstName.value = onQueryChanged
                    uiState.enteredFirstName = onQueryChanged
                    showFirstNameCheckIcon = onQueryChanged.isNotEmpty()
                },
                label = { Text(text = stringResource(id = R.string.hint_enter_first_name)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
                trailingIcon = {
                    if (showFirstNameCheckIcon && !isErrorFirstName && !isErrorInvalidFirstName) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = null
                        )
                    } else if (isErrorFirstName || isErrorInvalidFirstName) {
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    }
                }
            )

            if (isErrorFirstName) {
                ErrorText(error = stringResource(id = R.string.error_first_name))
            } else if (isErrorInvalidFirstName) {
                ErrorText(error = stringResource(id = R.string.error_special_character))
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(end = 16.dp)
                    .padding(start = 16.dp),
                value = textStateLastName.value,
                isError = isErrorLastName,
                onValueChange = { onQueryChanged ->
                    isErrorLastName = false
                    isErrorInvalidLastName = false
                    textStateLastName.value = onQueryChanged
                    uiState.enteredLastName = onQueryChanged
                    showLastNameCheckIcon = onQueryChanged.isNotEmpty()
                },
                label = { Text(text = stringResource(id = R.string.hint_enter_last_name)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
                trailingIcon = {
                    if (showLastNameCheckIcon && !isErrorLastName && !isErrorInvalidLastName) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = null
                        )
                    } else if (isErrorLastName || isErrorInvalidLastName) {
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    }
                }
            )

            if (isErrorLastName) {
                ErrorText(error = stringResource(id = R.string.error_last_name))
            } else if (isErrorInvalidLastName) {
                ErrorText(error = stringResource(id = R.string.error_special_character))
            }


            var expanded by remember { mutableStateOf(false) }
            var selectedCountryName by remember { mutableStateOf("") }
            var isErrorCountry by rememberSaveable { mutableStateOf(false) }

            var textfieldSize by remember { mutableStateOf(Size.Zero) }

            val icon = if (expanded)
                Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
            else
                Icons.Filled.ArrowDropDown


            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .padding(start = 16.dp)
                    .padding(top = 8.dp)
            ) {
                OutlinedTextField(
                    value = selectedCountryName,
                    onValueChange = {
                        isErrorCountry = false
                        selectedCountryName = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .onGloballyPositioned { coordinates ->
                            //This value is used to assign to the DropDown the same width
                            textfieldSize = coordinates.size.toSize()
                        },
                    isError = isErrorCountry,
                    label = { Text(text = stringResource(id = R.string.hint_select_country)) },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { expanded = !expanded })
                    }
                )

                if (isErrorCountry) {
                    ErrorText(error = stringResource(id = R.string.error_country))
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(onClick = {
                            isErrorCountry = false
                            selectedCountryName = country.countryName
                            expanded = false
                            sendViewModel.updateSelectedCountry(country = country)
                        }) {
                            Row(
                                modifier =
                                Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = country.countryFlag),
                                    contentDescription = null,
                                )
                                Text(
                                    modifier =
                                    Modifier
                                        .padding(start = 16.dp),
                                    text = country.countryName
                                )
                                Text(
                                    modifier =
                                    Modifier
                                        .padding(start = 16.dp),
                                    text = "(${country.countryCode})"
                                )
                            }
                        }
                    }
                }

            }

            var isErrorPhoneNumber by rememberSaveable { mutableStateOf(false) }
            var isErrorInvalidPhoneNumber by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(end = 16.dp)
                    .padding(start = 16.dp),
                value = textStatePhoneNumber.value,
                isError = isErrorPhoneNumber,
                onValueChange = { onQueryChanged ->
                    isErrorPhoneNumber = false
                    isErrorInvalidPhoneNumber = false
                    textStatePhoneNumber.value = onQueryChanged
                    uiState.enteredPhoneNumber = onQueryChanged
                    showPhoneNumberCheckIcon =
                        onQueryChanged.isNotEmpty() && onQueryChanged.length == uiState.selectedCountry?.phoneLength
                },
                label = { Text(text = stringResource(id = R.string.hint_enter_phone_number)) },
                visualTransformation = PrefixTransformation("(${uiState.selectedCountry?.countryCode}) "),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(
                            id = uiState.selectedCountry?.countryFlag!!
                        ),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (showPhoneNumberCheckIcon && !isErrorPhoneNumber && !isErrorInvalidPhoneNumber) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = null
                        )
                    } else if (isErrorPhoneNumber || isErrorInvalidPhoneNumber) {
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    }
                }
            )

            if (isErrorPhoneNumber) {
                ErrorText(error = stringResource(id = R.string.error_phone_number))
            } else if (isErrorInvalidPhoneNumber) {
                ErrorText(error = stringResource(id = R.string.error_invalid_phone_number))
            }

            val textStateAmount = remember { mutableStateOf("") }
            var isErrorAmount by remember { mutableStateOf(false) }
            var isErrorInvalidAmount by remember { mutableStateOf(false) }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(end = 16.dp)
                    .padding(start = 16.dp),
                value = textStateAmount.value,
                onValueChange = {
                    isErrorAmount = false
                    isErrorInvalidAmount = false
                    textStateAmount.value = it
                    showAmountCheckIcon = it.isNotEmpty()
                    try {
                        val amount = Integer.parseInt(it, 2)
                        if (amount > 0) {
                            sendViewModel.updateAmount(it, amount)
                        }
                    } catch (e: NumberFormatException) {
                        // Ignore it's handled by validation
                    }
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
                visualTransformation = PrefixTransformation("BN "),
                label = { Text("Enter Amount") },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Lock,
                        contentDescription = "Enter Amount",
                    )
                },
                trailingIcon = {
                    if (showAmountCheckIcon && !isErrorAmount && !isErrorInvalidAmount) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = null
                        )
                    } else if (isErrorAmount || isErrorInvalidAmount) {
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    }
                },
                isError = isErrorAmount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            if (isErrorAmount) {
                ErrorText(error = stringResource(id = R.string.error_amount))
            } else if (isErrorInvalidAmount) {
                ErrorText(error = stringResource(id = R.string.error_invalid_amount))
            }

            AmountViewCard()


            Button(
                onClick = {

                    when {
                        uiState.enteredFirstName.isEmpty() -> {
                            isErrorFirstName = true
                        }

                        !Util.isValidName(uiState.enteredFirstName) -> {
                            isErrorInvalidFirstName = true
                        }

                        uiState.enteredLastName.isEmpty() -> {
                            isErrorLastName = true
                        }

                        !Util.isValidName(uiState.enteredLastName) -> {
                            isErrorInvalidLastName = true
                        }

                        selectedCountryName.isEmpty() -> {
                            isErrorCountry = true
                        }

                        textStatePhoneNumber.value.isEmpty() -> {
                            isErrorPhoneNumber = true
                        }

                        textStatePhoneNumber.value.length != uiState.selectedCountry?.phoneLength -> {
                            isErrorInvalidPhoneNumber = true
                        }

                        uiState.amountBinary.isEmpty() -> {
                            isErrorAmount = true
                        }

                        !isBinaryNumber(textStateAmount.value) -> {
                            isErrorInvalidAmount = true
                        }

                        else -> {
                            val paymentData = uiState.selectedCountry?.countryName?.let {
                                PaymentData(
                                    uiState.enteredFirstName,
                                    uiState.enteredLastName,
                                    uiState.selectedCountry?.countryCode + textStatePhoneNumber.value,
                                    uiState.amountBinary,
                                    it
                                )
                            }

                            if (Util.isConnected(context)) {
                                sendViewModel.postPayment(paymentData)
                            } else {
                                Toast.makeText(
                                    context,
                                    "No Internet Connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(16.dp)
                    .height(60.dp)
            ) {
                Text(text = "Send Money")

            }
        }

    }
}

@Composable
fun ErrorText(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(start = 32.dp)
    )
}






