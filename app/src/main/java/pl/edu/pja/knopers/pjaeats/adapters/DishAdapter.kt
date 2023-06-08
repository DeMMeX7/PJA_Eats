package pl.edu.pja.knopers.pjaeats.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.knopers.pjaeats.databinding.ListItemBinding
import pl.edu.pja.knopers.pjaeats.model.Dish

class DishAdapter : RecyclerView.Adapter<DishItemHolder>() {

    private var dishes = mutableListOf<Dish>()
    var onClickAction: (Dish) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return DishItemHolder(binding).also { holder ->

            binding.root.setOnClickListener{
                onClickAction(dishes[holder.layoutPosition])
            }
        }
    }

    override fun getItemCount(): Int = dishes.size

    override fun onBindViewHolder(holder: DishItemHolder, position: Int) {
        holder.bind(dishes[position])
    }

    fun addDishes(dishes: List<Dish>) {
        val startPos = this.dishes.size
        this.dishes.addAll(dishes)
        notifyItemRangeInserted(startPos, dishes.size)
    }

    fun updateData(dishes: List<Dish>) {
        val size = this.dishes.size
        this.dishes.clear()
        notifyItemRangeRemoved(0, size)
        this.dishes.addAll(dishes)
        notifyItemRangeInserted(0, dishes.size)
    }
}