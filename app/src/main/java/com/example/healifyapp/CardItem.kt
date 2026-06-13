package com.example.healifyapp

import androidx.annotation.StringRes

data class CardItem(
    @StringRes val titleResId: Int,
    val categoryKey: String,
    val imageResId: Int
)
