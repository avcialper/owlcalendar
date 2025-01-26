package com.avcialper.owlcalendar.data.models

data class SelectedDate(val date: String, var calendarPosition: Int) {
    /**
     * Increment calendar position.
     */
    fun increasePosition() {
        calendarPosition++
    }

    /**
     * Decrement calendar position.
     */
    fun decreasePosition() {
        calendarPosition--
    }
}