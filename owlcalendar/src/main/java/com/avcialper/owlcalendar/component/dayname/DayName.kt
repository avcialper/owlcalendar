package com.avcialper.owlcalendar.component.dayname

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.data.repositories.DateRepository

internal class DayName @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        val layoutInflater = LayoutInflater.from(context)
        val days = DateRepository.getLocalizedDayNames()
        days.forEach { dayName ->
            val dayNameView =
                layoutInflater.inflate(R.layout.calendar_day_name, this, false) as TextView
            dayNameView.text = dayName
            addView(dayNameView)
        }
    }


}