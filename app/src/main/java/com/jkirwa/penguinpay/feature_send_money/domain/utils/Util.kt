package com.jkirwa.penguinpay.feature_send_money.domain.utils

object Util {
    fun isValidName(name: String): Boolean {
        return name.matches("^[A-Za-z,.'-]+\$".toRegex())
    }

     fun isBinaryNumber(binary: String): Boolean {
        return try {
            Integer.parseInt(binary, 2) > 0
        } catch (e: NumberFormatException) {
            false
        }
    }
}