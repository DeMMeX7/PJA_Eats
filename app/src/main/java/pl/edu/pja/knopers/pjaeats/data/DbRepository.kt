package pl.edu.pja.knopers.pjaeats.data

import android.content.Context
import pl.edu.pja.knopers.pjaeats.data.db.DishDB
import pl.edu.pja.knopers.pjaeats.data.db.DishEntity
import pl.edu.pja.knopers.pjaeats.model.Dish

class DbRepository(context: Context) : Repository {
    val db by lazy { DishDB.open(context) }
    val res by lazy { context.resources }
    val pckg = context.packageName

    override fun getDishes(): List<Dish> =
        db.dishes.all().map{
            Dish(
                it.id,
                it.name,
                res.getIdentifier(it.image, "drawable", pckg ),
                it.description,
                it.latitude,
                it.longitude
            )
        }

    override fun add(dish: Dish) {
        val dishEntity = DishEntity(
            id = dish.id,
            name = dish.name,
            image = res.getResourceEntryName(dish.image),
            description = dish.description,
            latitude = dish.latitude,
            longitude = dish.longitude

        )
        db.dishes.add(dishEntity)
    }

    override fun getDish(dishId: Long): Dish =
        db.dishes.get(dishId).let {
            Dish(
                it.id,
                it.name,
                res.getIdentifier(it.image, "drawable", pckg ),
                it.description,
                it.latitude,
                it.longitude
            )
        }

}