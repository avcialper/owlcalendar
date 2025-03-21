package com.avcialper.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.avcialper.owlcalendar.data.models.LineDate
import com.avcialper.owlcalendar.data.models.LineSelectedDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.sample.R
import com.avcialper.sample.databinding.FragmentSampleBinding

class SampleFragment : Fragment() {

    private var _binding: FragmentSampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSampleBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val purple = getColor(R.color.purple_transparent)
        val green = getColor(R.color.green_transparent)
        val red = getColor(R.color.red_transparent)

        binding.owlCalendar.apply {
            val markedDays = listOf(
                MarkedDay(startYear, startMonth, 8, purple),
                MarkedDay(startYear, startMonth, 19, green),
                MarkedDay(startYear, startMonth, 28, red),
            )

            val lineDate = LineDate(
                LineSelectedDate(startYear, startMonth, 10),
                LineSelectedDate(startYear, startMonth, 15)
            )
            setLineDate(lineDate)

            addMarkedDays(markedDays)
        }
    }

    private fun getColor(id: Int): Int = ContextCompat.getColor(requireContext(), id)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}