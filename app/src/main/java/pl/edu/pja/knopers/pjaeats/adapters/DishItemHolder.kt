package pl.edu.pja.knopers.pjaeats.adapters

import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.knopers.pjaeats.databinding.ListItemBinding
import pl.edu.pja.knopers.pjaeats.model.Dish

class DishItemHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dish: Dish) {
        binding.title.text = dish.name
        binding.imageView.setImageResource(dish.image)
    }
}