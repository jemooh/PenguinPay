package com.jkirwa.penguinpay.feature_send_money.presentation.sendmoney

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkirwa.penguinpay.feature_send_money.data.data_source.local.PaymentData
import androidx.compose.ui.window.Dialog
import com.jkirwa.penguinpay.R
import com.jkirwa.penguinpay.feature_send_money.MainActivity

@Composable
fun SuccessDialog(paymentData: PaymentData) {
    val openDialog = remember { mutableStateOf(true) }
    val context = LocalContext.current
    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color.White
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Payment Successfully ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Image(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(100.dp)
                            .height(100.dp),
                        painter = painterResource(id = R.drawable.ic_sync),
                        contentDescription = null,
                    )

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Recipient Name",
                            fontSize = 16.sp
                        )
                        Text(
                            text = String.format(
                                "%s %s",
                                paymentData.firstName,
                                paymentData.lastName
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 3.dp)

                        )
                        Text(
                            text = "Phone Number",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        Text(
                            text = paymentData.phoneNumber,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                        Text(
                            text = "Amount",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        Text(
                            text = paymentData.amount,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                        Text(
                            text = "Country",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        Text(
                            text = paymentData.country,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 3.dp)
                        )

                        Button(
                            onClick = {
                                openDialog.value = false
                                context.startActivity(Intent(context, MainActivity::class.java))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .padding(16.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Done")
                        }

                    }
                }

            }
        }
    }
}