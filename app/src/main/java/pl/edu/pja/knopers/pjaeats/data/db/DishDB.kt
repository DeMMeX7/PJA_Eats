package pl.edu.pja.knopers.pjaeats.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [DishEntity::class],
    version = 2

)
abstract class DishDB : RoomDatabase() {
    abstract val dishes: DishDao

    companion object{
        fun open(ctx: Context) =
            Room.databaseBuilder(ctx, DishDB::class.java, "DishDB" )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
