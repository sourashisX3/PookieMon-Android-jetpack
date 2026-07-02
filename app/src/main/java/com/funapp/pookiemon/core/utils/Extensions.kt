package com.funapp.pookiemon.core.utils

fun String.isValidEmail(): Boolean {
    return isNotEmpty() && matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))
}

fun String.isValidPassword(): Boolean {
    return length >= 8
}

fun String.isValidUsername(): Boolean {
    return length >= 3
}
