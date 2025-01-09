package com.avcialper.owlcalendar.util.extensions

import java.util.Locale

fun Int.convertToString(): String {
    val locale = Locale.getDefault()
    return String.format(locale, "%d", this)
}