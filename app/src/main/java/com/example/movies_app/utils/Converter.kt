@file:JvmName("Converter")

package com.example.movies_app.utils


import android.content.Context
import com.example.movies_app.R

fun priceAndAvailabilityToString(price: String, isAvailable: Boolean, context: Context): String {
    return when (isAvailable) {
        true -> String.format("%s %s", price, context.getString(R.string.available))
        false -> String.format("%s %s", price, context.getString(R.string.not_available))
    }
}