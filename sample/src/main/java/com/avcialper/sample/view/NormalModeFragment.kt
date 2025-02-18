package com.avcialper.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avcialper.sample.databinding.FragmentNormalModeBinding

class NormalModeFragment : Fragment() {

    private var _binding: FragmentNormalModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNormalModeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            owlCalendar.setOnDayClickListener {
                val (date, dayOfMonth, dayName, dayOfWeek, month, year) = it
                val text = """
                    Date: $date
                    Day of Month: $dayOfMonth
                    Day Name: $dayName
                    Day of Week: ${dayOfWeek + 1}
                    Month: ${month + 1}
                    Year: $year
                """.trimIndent()
                selectedDate.text = text
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}