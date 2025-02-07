package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDate

data class Date(
    /**
     * Date as a string in the format `dd.MM.yyyy`.
     */
    val date: String,
    /**
     * Day of the month. Start day `1`.
     */
    val dayOfMonth: Int,
    /**
     * Name of the day of the week.
     */
    val dayName: String,
    /**
     * Index of the day of the week. Is between 0 to 6.
     */
    val dayOfWeek: Int,
    /**
     * Month of the year. Start month `0`.
     */
    val month: Int,
    /**
     * Year field.
     */
    val year: Int
) {
    /**
     * Checks if the date is today.
     * @return `true` if the date is today, `false` otherwise.
     */
    fun isToday(): Boolean = date == JDateTime.instance.date

    /**
     * Checks if the date is after another date.
     * @param other The date to compare with.
     * @return `true` if the date is after the other date, `false` otherwise.
     */
    fun isAfter(other: JDate): Boolean {
        return year > other.year || (year == other.year && month > other.month) ||
                (year == other.year && month == other.month && dayOfMonth > other.dayOfMonth)
    }

    /**
     * Checks if the date is before another date.
     * @param other The date to compare with.
     * @return `true` if the date is before the other date, `false` otherwise.
     */
    fun isBefore(other: JDate): Boolean {
        return year < other.year || (year == other.year && month < other.month) ||
                (year == other.year && month == other.month && dayOfMonth < other.dayOfMonth)
    }

    /**
     * Checks if the date is before today.
     * @return `true` if the date is before today, `false` otherwise.
     */
    fun isBeforeToday(): Boolean {
        val today = JDateTime.instance
        return year < today.year || (year == today.year && month < today.month) ||
                (year == today.year && month == today.month && dayOfMonth < today.dayOfMonth)
    }

    /**
     * Checks if the date is after today.
     * @return `true` if the date is after today, `false` otherwise.
     */
    fun isAfterToday(): Boolean {
        val today = JDateTime.instance
        return year > today.year || (year == today.year && month > today.month) ||
                (year == today.year && month == today.month && dayOfMonth > today.dayOfMonth)
    }

    /**
     * Checks if the date is the same as another date.
     * @param other The date to compare with.
     * @return `true` if the date is the same as the other date, `false` otherwise.
     */
    fun isSameDay(other: Date): Boolean = this == other
}

/**
 * Convert [JDate] to [Date].
 */
internal fun JDate.toDate(): Date {
    val (date, dayOfMonth, dayName, dayOfWeek, month, year) = this
    return Date(date, dayOfMonth, dayName, dayOfWeek, month, year)
}

/**
 * Convert [JDate] list to [Date] list.
 */
internal fun List<JDate>.toDateList(): List<Date> = this.map { it.toDate() }

/**
 * Add empty days the list for first day of the month, and add empty days to the end of the list for
 * standard view high.
 * @return List of nullable [Date] objects.
 */
internal fun List<Date>.adjustDay(): List<Date?> {
    val firstDayIndex = this[0].dayOfWeek
    val emptyDays = MutableList<Date?>(firstDayIndex) { null }
    emptyDays.addAll(this)

    val dayCount = emptyDays.size
    if (dayCount < 36) {
        val diff = 36 - dayCount
        val days = List(diff) { null }
        emptyDays.addAll(days)
    }

    return emptyDays
}

/**
 * Find selected date index.
 * @param day Selected date
 */
internal fun List<Date?>.findIndex(day: SelectedDate): Int = this.indexOfFirst {
    it?.year == day.year && it.month == day.month && it.dayOfMonth == day.dayOfMonth
}

/**
 * Find date index.
 * @param day Selected date
 */
internal fun List<Date?>.findIndex(day: Date): Int = this.indexOfFirst {
    it?.year == day.year && it.month == day.month && it.dayOfMonth == day.dayOfMonth
}

/**
 * Find marked date index.
 * @param day Selected date
 */
internal fun List<Date?>.findIndex(day: MarkedDay): Int = this.indexOfFirst {
    it?.year == day.year && it.month == day.month && it.dayOfMonth == day.dayOfMonth
}