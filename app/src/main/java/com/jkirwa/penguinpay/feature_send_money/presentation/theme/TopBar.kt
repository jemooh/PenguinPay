package com.jkirwa.penguinpay.feature_send_money.presentation.theme

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopBar() {

    TopAppBar(
        title = { Text("Penguin Pay") }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    PenguinPayAppTheme {
        TopBar()
    }
}
