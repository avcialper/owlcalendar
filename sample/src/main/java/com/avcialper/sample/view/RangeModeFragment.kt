package com.avcialper.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avcialper.sample.databinding.FragmentRangeModeBinding

class RangeModeFragment : Fragment() {

    private var _binding: FragmentRangeModeBinding? = null
    private val binding get() = _binding!!

    private var isRangeDateSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRangeModeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            owlCalendar.setOnLineDateChangeListener { startLineDate, endLineDate ->
                if (startLineDate == null || endLineDate == null) {
                    isRangeDateSelected = false
                    return@setOnLineDateChangeListener
                }

                isRangeDateSelected = true

                val (sYear, sMonth, sDayOfMonth) = startLineDate
                val (eYear, eMonth, eDayOfMonth) = endLineDate
                val text = """
                    Start Line Date: $sDayOfMonth/${sMonth + 1}/$sYear
                    End Line Date: $eDayOfMonth/${eMonth + 1}/$eYear
                """.trimIndent()
                lineDateTV.text = text
            }

            owlCalendar.setOnDayClickListener {
                if (isRangeDateSelected) return@setOnDayClickListener

                val (date, dayOfMonth, dayName, dayOfWeek, month, year) = it
                val text = """
                    Date: $date
                    Day of Month: $dayOfMonth
                    Day Name: $dayName
                    Day of Week: ${dayOfWeek + 1}
                    Month: ${month + 1}
                    Year: $year
                """.trimIndent()
                lineDateTV.text = text
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}