package com.avcialper.owlcalendar.util.extensions

import com.avcialper.owlcalendar.helper.CalendarManager.locale

/**
 * Convert integer to string use current locale.
 */
internal fun Int.convertToString(): String {
    return String.format(locale, "%d", this)
}