package com.avcialper.owlcalendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avcialper.owlcalendar.databinding.YearMonthPickerBinding
import com.avcialper.owlcalendar.helper.CalendarManager.attrs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class YearMonthPicker(
    private val currentYear: Int,
    private val currentMonth: Int,
    private val onDateSelected: (year: Int, month: Int) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: YearMonthPickerBinding? = null
    private val binding: YearMonthPickerBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = YearMonthPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            year.apply {
                minValue = currentYear - 50
                maxValue = currentYear + 50
                value = currentYear
            }

            month.apply {
                minValue = 1
                maxValue = 12
                value = currentMonth + 1
            }

            val textColor = attrs.pickerButtonTextColor
            val text = attrs.pickerButtonText
            complete.apply {
                setTextColor(textColor)
                this.text = text
            }

            complete.setOnClickListener {
                onDateSelected(year.value, month.value - 1)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}