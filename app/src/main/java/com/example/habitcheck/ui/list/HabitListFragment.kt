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
    }

    private fun initRecyclerView() {
        habitAdapter = HabitListAdapter(
            onItemClick = { },
            onCheckChange = { _, _ -> }
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






