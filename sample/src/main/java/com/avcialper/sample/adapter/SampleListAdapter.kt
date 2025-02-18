package com.avcialper.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.sample.databinding.SampleItemBinding
import com.avcialper.sample.model.Sample

class SampleListAdapter(
    private val samples: List<Sample>,
    private val onItemClicked: (NavDirections) -> Unit
) : RecyclerView.Adapter<SampleListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SampleItemBinding.inflate(layoutInflater, parent, false)
        return SampleListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SampleListViewHolder, position: Int) {
        val sample = samples[position]
        holder.bind(sample, onItemClicked)
    }

    override fun getItemCount(): Int = samples.size
}