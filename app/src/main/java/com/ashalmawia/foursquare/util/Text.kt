package com.ashalmawia.foursquare.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

sealed class Text {
    data class StringPlain(val value: String) : Text()
    data class StringResource(@StringRes val value: Int) : Text()
}

fun showToast(context: Context, text: Text, length: Int) {
    when (text) {
        is Text.StringPlain -> Toast.makeText(context, text.value, length).show()
        is Text.StringResource -> Toast.makeText(context, text.value, length).show()
    }
}