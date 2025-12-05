package com.example.habitcheck.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitcheck.data.HabitDatabase
import com.example.habitcheck.data.repository.HabitRepository
import com.example.habitcheck.databinding.FragmentHabitListBinding
import com.example.habitcheck.viewmodel.HabitViewModel
import com.example.habitcheck.viewmodel.HabitViewModelFactory

class HabitListFragment : Fragment() {

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private lateinit var habitAdapter: HabitListAdapter

    private val habitViewModel: HabitViewModel by viewModels {
        val db = HabitDatabase.getDatabase(requireContext())
        val repo = HabitRepository(db.habitDao())
        HabitViewModelFactory(repo)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddHabit.setOnClickListener {
            val action = HabitListFragmentDirections
                .actionHabitListFragmentToCreateUpdateHabitFragment()
            findNavController().navigate(action)
        }

        initRecyclerView()
        observeHabits()

        binding.deleteButton.isEnabled = false
        binding.deleteButton.alpha = 0.4f

        binding.deleteButton.setOnClickListener {
            val ids = habitAdapter.selectedIds.toList()
            if (ids.isNotEmpty()) {
                habitViewModel.deleteHabitById(ids)   // <- 변경
                habitAdapter.selectedIds.clear()
                habitAdapter.notifyDataSetChanged()

                binding.deleteButton.isEnabled = false
                binding.deleteButton.alpha = 0.4f
            }
        }
    }

    private fun initRecyclerView() {
        habitAdapter = HabitListAdapter(
            onEditClick = { habit ->
                val action = HabitListFragmentDirections
                    .actionHabitListFragmentToCreateUpdateHabitFragment(habit.id)
                findNavController().navigate(action) },

            onSelectionChanged = { selectedCount ->
                binding.deleteButton.isEnabled = selectedCount > 0
                binding.deleteButton.alpha = if (selectedCount > 0) 1.0f else 0.4f
            }
        )

        binding.rvHabitList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = habitAdapter
        }
    }


    private fun observeHabits() {
        habitViewModel.allHabits.observe(viewLifecycleOwner) { habits ->
            habitAdapter.submitList(habits)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






