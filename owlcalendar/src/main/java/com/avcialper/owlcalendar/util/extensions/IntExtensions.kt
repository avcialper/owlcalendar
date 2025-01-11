package com.avcialper.owlcalendar.util.extensions

import java.util.Locale

/**
 * Convert integer to string use current locale.
 */
fun Int.convertToString(): String {
    val locale = Locale.getDefault()
    return String.format(locale, "%d", this)
}