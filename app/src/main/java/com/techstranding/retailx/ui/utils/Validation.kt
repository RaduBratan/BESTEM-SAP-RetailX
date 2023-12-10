package com.techstranding.retailx.ui.utils

import androidx.compose.ui.text.input.TextFieldValue

internal fun TextFieldValue.isValidEmail(): Boolean {
    val pattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

    return text.matches(pattern.toRegex()) && text.isNotEmpty()
}

internal fun TextFieldValue.isValidPassword(): Boolean {
    return text.length >= 10 && text.isNotEmpty()
}