package pl.edu.pja.knopers.pjaeats.model

import androidx.annotation.DrawableRes

data class Dish(
    val id: Long,
    val name: String,
    @DrawableRes
    val image: Int,
    val description: String,
    val latitude: Double,
    val longitude: Double
)
