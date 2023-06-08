package pl.edu.pja.knopers.pjaeats.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DishDao {
    @Query("SELECT * FROM dish;")
    fun all(): List<DishEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(dish: DishEntity)

    @Query("SELECT * FROM dish WHERE id =:dishId")
    fun get(dishId: Long): DishEntity

    @Delete
    fun remove(dish: DishEntity)
}