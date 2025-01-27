package com.avcialper.owlcalendar.util.extensions

import android.content.Context
import android.widget.Toast

/**
 * Push toast message to the screen.
 */
internal fun Context.pushToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}