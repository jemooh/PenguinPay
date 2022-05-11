package com.jkirwa.penguinpay.feature_send_money.domain.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

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

    fun isConnected(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = oldVersionConnectivity(type)

                }
            }
        }

        return result

    }

    private fun oldVersionConnectivity(type: Int): Boolean {
        return when (type) {
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> true
            ConnectivityManager.TYPE_ETHERNET -> true
            else -> false
        }
    }

}