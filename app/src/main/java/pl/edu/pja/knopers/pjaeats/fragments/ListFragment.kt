package pl.edu.pja.knopers.pjaeats.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.pja.knopers.pjaeats.Destination
import pl.edu.pja.knopers.pjaeats.adapters.DishAdapter
import pl.edu.pja.knopers.pjaeats.data.DbRepository
import pl.edu.pja.knopers.pjaeats.data.MemoryRepository
import pl.edu.pja.knopers.pjaeats.data.Repository
import pl.edu.pja.knopers.pjaeats.databinding.FragmentListBinding
import pl.edu.pja.knopers.pjaeats.navigate
import kotlin.concurrent.thread


class ListFragment : Fragment() {

    private val repository: Repository by lazy { DbRepository(requireContext().applicationContext) }

    lateinit var binding: FragmentListBinding
    lateinit var adapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also {
                adapter = DishAdapter().apply {
                    onClickAction = {
                        navigate(Destination.Edit(it))
                    }
                }
                binding = it
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    adapter = this@ListFragment.adapter
                }
                binding.addButton.setOnClickListener(::addDishes)
            }.root
    }

    override fun onResume() {
        super.onResume()
        thread {
            val dishes = repository.getDishes()
            binding.root.post {
                adapter.updateData(dishes)
            }
        }

    }

    private fun addDishes(view: View?) {
        navigate(Destination.Add)
    }

}