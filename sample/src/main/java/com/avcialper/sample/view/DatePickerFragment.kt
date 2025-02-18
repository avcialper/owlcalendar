package com.avcialper.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avcialper.sample.databinding.FragmentDatePickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DatePickerFragment(
    private val currentYear: Int,
    private val currentMonth: Int,
    private val onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentDatePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatePickerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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

            dayOfMonth.apply {
                minValue = 1
                maxValue = 31
                value = 1
            }

            addMarkedDay.setOnClickListener {
                onDateSelected(year.value, month.value - 1, dayOfMonth.value)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}