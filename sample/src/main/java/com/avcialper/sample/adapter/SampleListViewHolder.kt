package com.avcialper.sample.adapter

import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.sample.databinding.SampleItemBinding
import com.avcialper.sample.model.Sample

class SampleListViewHolder(
    private val binding: SampleItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(sample: Sample, onItemClicked: (NavDirections) -> Unit) {
        binding.apply {
            val (title, description) = sample
            this.title.text = title
            this.description.text = description

            root.setOnClickListener {
                val direction = sample.direction
                onItemClicked.invoke(direction)
            }
        }
    }

}