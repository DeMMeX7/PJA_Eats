package pl.edu.pja.knopers.pjaeats

import pl.edu.pja.knopers.pjaeats.model.Dish

sealed class Destination {
    object List: Destination()
    object Add : Destination()
    data class Edit(val dish : Dish) : Destination()
}