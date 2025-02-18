package com.avcialper.sample.model

import androidx.navigation.NavDirections

data class Sample(
    val title: String,
    val description: String,
    val direction: NavDirections
)