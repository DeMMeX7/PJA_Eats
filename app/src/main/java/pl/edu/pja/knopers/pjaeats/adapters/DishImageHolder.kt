package pl.edu.pja.knopers.pjaeats.adapters

import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.knopers.pjaeats.databinding.ImageItemBinding
import pl.edu.pja.knopers.pjaeats.databinding.ListItemBinding

class DishImageHolder(private val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(@DrawableRes dishImage: Int, isSelected: Boolean) {
        binding.image.setImageResource(dishImage)
        binding.frame.isVisible = isSelected
    }
}