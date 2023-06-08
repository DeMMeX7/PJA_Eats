package pl.edu.pja.knopers.pjaeats.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.knopers.pjaeats.R
import pl.edu.pja.knopers.pjaeats.databinding.ImageItemBinding

class DishImagesAdapter : RecyclerView.Adapter<DishImageHolder>() {

    private val disheImages = listOf(R.drawable.pizza, R.drawable.pumpkin, R.drawable.spaghetti)
    private var selectedId: Int = 0
    @get:DrawableRes
    var selected: Int
        get() = disheImages[selectedId]
        set(@DrawableRes value) {
            val oldPos = selectedId
            selectedId = disheImages.indexOf(value)
            notifyItemChanged(oldPos)
            notifyItemChanged(selectedId)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)
        return DishImageHolder(binding).also { holder ->
            binding.root.setOnClickListener {
                selectImage(holder.layoutPosition)
            }
        }
    }

    override fun getItemCount(): Int = disheImages.size

    override fun onBindViewHolder(holder: DishImageHolder, position: Int) {
        holder.bind(disheImages[position], isSelected = selectedId == position)
    }

    private fun selectImage(position: Int) {
        val oldPosition = selectedId
        selectedId = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(selectedId)
    }
}