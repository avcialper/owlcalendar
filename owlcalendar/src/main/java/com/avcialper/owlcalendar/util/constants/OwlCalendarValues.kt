package com.avcialper.owlcalendar.util.constants

import com.avcialper.jdatetime.JDateTime
import com.avcialper.owlcalendar.data.models.SelectedDate

internal object OwlCalendarValues {

    var selectedDate: SelectedDate = SelectedDate(JDateTime.instance.date, 1)

}