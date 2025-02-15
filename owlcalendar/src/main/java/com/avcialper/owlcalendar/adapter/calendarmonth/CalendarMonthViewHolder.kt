package com.avcialper.owlcalendar.adapter.calendarmonth

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.helper.CalendarManager
import com.avcialper.owlcalendar.helper.CalendarManager.attrs
import com.avcialper.owlcalendar.helper.CalendarManager.data
import com.avcialper.owlcalendar.util.extensions.convertToString

internal class CalendarMonthViewHolder(
    binding: CalendarDayBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val root = binding.root
    private val context = root.context

    private val defaultTextColor = root.currentTextColor

    private val selectedDrawable = getDrawableAsGradientDrawable(R.drawable.day_focused)
    private val markedDayDrawable = getDrawableAsGradientDrawable(R.drawable.day_marked)

    private var lineDrawable: GradientDrawable? = null
    private val lineStartDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_start)
    private val lineEndDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_end)
    private val lineInRangeDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_in_range)

    fun bind(
        day: Date,
        onDayClickListener: (Date) -> Unit
    ) {
        setTextColor(day)
        setBackground(day)

        val dayOfMonth = day.dayOfMonth.convertToString()
        root.apply {
            text = dayOfMonth
            setOnClickListener {
                onDayClickListener.invoke(day)
            }
        }

    }

    /**
     * Set text color of the day. If it is today, set orange color.
     * @param day [Date] object
     */
    private fun setTextColor(day: Date) {
        val todayTextColor = attrs.todayTextColor
        val calendarDayTextColor = attrs.dayTextColor
        val defaultTextColor =
            if (calendarDayTextColor == 0) defaultTextColor else calendarDayTextColor

        val isToday = day.isToday()

        val textColor = when {
            isToday -> todayTextColor
            else -> defaultTextColor
        }

        root.setTextColor(textColor)
    }

    /**
     * Set background of the day. If it is selected, set orange background.
     * @param day [Date] object
     */
    private fun setBackground(day: Date) {
        val isSelected = data.selectedDate.isEqual(day)
        val markedDay = CalendarManager.findMarkedDay(day)
        val lineDate = data.lineDate

        if (markedDay != null)
            markedDayDrawable.setColor(markedDay.color)

        if (lineDate != null) {
            lineDrawable = when {
                lineDate.isStart(day) -> lineStartDrawable
                lineDate.isEnd(day) -> lineEndDrawable
                lineDate.isInRange(day) -> lineInRangeDrawable
                else -> null
            }
            val color =
                if (isSelected) attrs.selectedDateBackgroundColor
                else markedDay?.color ?: attrs.lineBackgroundColor

            lineDrawable?.setColor(color)
        }

        val isRangeSelectable = attrs.calendarMode.isRangeSelectable()
        val selectedBackgroundColor =
            if (isRangeSelectable) attrs.lineBackgroundColor else attrs.selectedDateBackgroundColor
        selectedDrawable.setColor(selectedBackgroundColor)

        val drawable = when {
            lineDrawable != null -> lineDrawable
            isSelected -> selectedDrawable
            markedDay != null -> markedDayDrawable
            else -> null
        }

        root.background = drawable
    }

    private fun getDrawableAsGradientDrawable(id: Int): GradientDrawable {
        return ContextCompat.getDrawable(context, id)?.mutate() as GradientDrawable
    }

}
