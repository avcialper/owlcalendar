package com.avcialper.owlcalendar

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.avcialper.owlcalendar.data.repositories.DateRepository
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
        initUI()
    }

    private fun initUI() = with(binding) {
        year.apply {
            minValue = currentYear - 50
            maxValue = currentYear + 50
            value = currentYear
        }

        month.apply {
            minValue = 1
            maxValue = 12
            value = currentMonth + 1
            displayedValues = DateRepository.getLocalizedMonthNames().toTypedArray()
        }

        val backgroundColor = attrs.pickerBackgroundColor
        if (backgroundColor != 0) {
            val backgroundDrawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.bottom_sheet_background)
                    ?.mutate() as GradientDrawable
            backgroundDrawable.setColor(backgroundColor)
            root.background = backgroundDrawable
        }


        val textColor = attrs.pickerButtonTextColor
        val buttonBackgroundColor = attrs.pickerButtonBackgroundColor
        val buttonBackgroundDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.picker_button_background)
                ?.mutate() as GradientDrawable
        buttonBackgroundDrawable.setColor(buttonBackgroundColor)

        val text = attrs.pickerButtonText
        complete.apply {
            setTextColor(textColor)
            background = buttonBackgroundDrawable
            this.text = text
            setOnClickListener {
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