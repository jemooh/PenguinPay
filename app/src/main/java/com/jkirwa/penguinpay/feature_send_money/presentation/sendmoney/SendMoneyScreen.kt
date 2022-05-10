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
fun SendMoneyScreen() {
    val ratesViewModel = getViewModel<ExchangeRatesViewModel>()
    val uiState = ratesViewModel.state.collectAsState().value
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        var showLastNameCheckIcon by remember { mutableStateOf(false) }
        var showFirstNameCheckIcon by remember { mutableStateOf(false) }
        var showPhoneNumberCheckIcon by remember { mutableStateOf(false) }
        val textStatePhoneNumber = remember { mutableStateOf("") }
        val textStateFirstName = remember { mutableStateOf("") }
        val textStateLastName = remember { mutableStateOf("") }

        Text(
            text = stringResource(id = R.string.subtitle_fill_details),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(bottom = 16.dp)
                .align(CenterHorizontally)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(end = 16.dp)
                .padding(start = 16.dp),
            value = textStateFirstName.value,
            onValueChange = { onQueryChanged ->
                textStateFirstName.value = onQueryChanged
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
                if (showFirstNameCheckIcon) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = null
                    )
                }
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(end = 16.dp)
                .padding(start = 16.dp),
            value = textStateLastName.value,
            onValueChange = { onQueryChanged ->
                textStateLastName.value = onQueryChanged
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
                if (showLastNameCheckIcon) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = null
                    )
                }
            }
        )


        var expanded by remember { mutableStateOf(false) }
        var selectedCountryName by remember { mutableStateOf("") }

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
                onValueChange = { selectedCountryName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    },
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
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(onClick = {
                        selectedCountryName = country.countryName
                        expanded = false
                        ratesViewModel.updateSelectedCountry(country = country)
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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(end = 16.dp)
                .padding(start = 16.dp),
            value = textStatePhoneNumber.value,
            onValueChange = { onQueryChanged ->
                textStatePhoneNumber.value = onQueryChanged
                showPhoneNumberCheckIcon = onQueryChanged.isNotEmpty() && onQueryChanged.length == uiState.selectedCountry?.phoneLength
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
                if (showPhoneNumberCheckIcon) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = null
                    )
                }
            }
        )

        val textStateAmount = remember { mutableStateOf("") }
        var validation by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(end = 16.dp)
                .padding(start = 16.dp),
            value = textStateAmount.value,
            onValueChange = {
                textStateAmount.value = it
                try {
                    val amount = Integer.parseInt(it, 2)
                    if (amount > 0) {
                        ratesViewModel.updateAmount(it, amount)
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
            isError = validation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        AmountViewCard()

        Button(
            onClick = {

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



