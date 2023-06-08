package pl.edu.pja.knopers.pjaeats.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish")
data class DishEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val image: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)