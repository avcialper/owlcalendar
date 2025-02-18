package com.avcialper.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.sample.R
import com.avcialper.sample.databinding.FragmentDayMarkingBinding

class DayMarkingFragment : Fragment() {

    private var _binding: FragmentDayMarkingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayMarkingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val purple = getColor(R.color.purple_transparent)
        val green = getColor(R.color.green_transparent)
        val red = getColor(R.color.red_transparent)
        val blue = getColor(R.color.blue_transparent)
        val yellow = getColor(R.color.yellow_transparent)

        val colors = listOf(purple, green, red, blue, yellow)

        binding.apply {

            selectDate.setOnClickListener {
                val picker = DatePickerFragment(
                    owlCalendar.startYear,
                    owlCalendar.startMonth,
                ) { year, month, dayOfMonth ->
                    val randomColor = colors.random()
                    val markedDay = MarkedDay(year, month, dayOfMonth, randomColor)
                    owlCalendar.addMarkedDay(markedDay)
                }
                val manager = requireActivity().supportFragmentManager
                picker.show(manager, "marking_data_picker")
            }

        }
    }

    private fun getColor(id: Int): Int = ContextCompat.getColor(requireContext(), id)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}