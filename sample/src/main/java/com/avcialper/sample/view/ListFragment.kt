package com.avcialper.sample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.avcialper.sample.adapter.SampleListAdapter
import com.avcialper.sample.databinding.FragmentListBinding
import com.avcialper.sample.model.Sample
import com.google.android.material.divider.MaterialDividerItemDecoration

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        val samples = listOf(
            Sample(
                "Basic Calendar",
                "The default mode with no special markings on the calendar, showing only the basic month-day display and navigation.",
                ListFragmentDirections.toNormal()
            ),
            Sample(
                "Single Selectable Mode",
                "A mode where the user can select one date at a time, and only that date’s value is taken.",
                ListFragmentDirections.toSingle()
            ),
            Sample(
                "Range Selectable Mode",
                "A mode for selecting a date range, where a start and end date can be chosen, and a line date can be added.",
                ListFragmentDirections.toRange()
            ),
            Sample(
                "Custom Day Marking",
                "Allows the user to select a specific date and color to mark on the calendar.",
                ListFragmentDirections.toMarking()
            ),
            Sample(
                "Sample Scenario",
                "Displays a random starting view with predefined marked days and one line date for example purpose.",
                ListFragmentDirections.toSample()
            )
        )

        val navController = findNavController()
        val adapter = SampleListAdapter(samples) { direction ->
            navController.navigate(direction)
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val separator = MaterialDividerItemDecoration(context, layoutManager.orientation).apply {
            isLastItemDecorated = false
        }

        binding.sampleRV.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
            addItemDecoration(separator)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}