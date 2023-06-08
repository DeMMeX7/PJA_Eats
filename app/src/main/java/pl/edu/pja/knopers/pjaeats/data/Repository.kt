package pl.edu.pja.knopers.pjaeats.data

import pl.edu.pja.knopers.pjaeats.model.Dish

interface Repository {
    fun getDishes(): List<Dish>
    fun add(dish: Dish)
    fun getDish(dishId: Long) : Dish
}