package pl.edu.pja.knopers.pjaeats.data

import pl.edu.pja.knopers.pjaeats.R
import pl.edu.pja.knopers.pjaeats.model.Dish

object MemoryRepository : Repository {
    private val dishes = mutableListOf<Dish>(
        Dish(1,"Pizza", R.drawable.pizza, "",52.2236096,20.9934412),
        Dish(2,"Spaghettii", R.drawable.spaghetti, "",52.2236096,20.9934412),
        Dish(3,"Pumpkin Soup", R.drawable.pumpkin, "",52.2236096,20.9934412),
    )
    override fun getDishes(): List<Dish> = dishes
    override fun add(dish: Dish) {
        if (dish.id == 0L) {
            dishes.add(dish.copy(id = dishes.map { it.id }.max() + 1))
        } else {
            dishes[dishes.indexOfFirst { it.id == dish.id }] = dish
        }
    }

    override fun getDish(dishId: Long) = requireNotNull(dishes.find { it.id == dishId })
    }
