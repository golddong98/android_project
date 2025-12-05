package com.example.habitcheck.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habitcheck.data.HabitDatabase
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.data.repository.HabitRepository
import com.example.habitcheck.databinding.FragmentCreateUpdateHabitBinding
import com.example.habitcheck.viewmodel.HabitViewModel
import com.example.habitcheck.viewmodel.HabitViewModelFactory

class CreateUpdateHabitFragment : Fragment() {

    private var _binding: FragmentCreateUpdateHabitBinding? = null
    private val binding get() = _binding!!
    private val args: CreateUpdateHabitFragmentArgs by navArgs()

    private val habitViewModel: HabitViewModel by activityViewModels {
        val habitDatabase = HabitDatabase.getDatabase(requireContext())
        val habitRepository = HabitRepository(habitDatabase.habitDao())
        HabitViewModelFactory(habitRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateUpdateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var habit: HabitEntity? = null

        binding.saveButton.isEnabled = false
        binding.name.doAfterTextChanged {
            text -> binding.saveButton.isEnabled = !text.isNullOrBlank()
        }
        val habitId = args.habitId
        if(habitId != -1){
            habitViewModel.getHabitById(habitId).observe(viewLifecycleOwner) {
                habitEntity ->
                if(habitEntity == null) return@observe
                habit = habitEntity
                binding.name.setText(habitEntity.name)
                binding.description.setText(habitEntity.description)
            }

        }
        binding.saveButton.setOnClickListener {
            val name = binding.name.text.toString()
            var description = binding.description.text.toString().ifEmpty { null }
            if(habitId != -1) {
                description = binding.description.text.toString()
                habitViewModel.updateHabit(habit!!.id, name, description)
            } else {
                habitViewModel.saveHabit(name, description)
            }

            findNavController().popBackStack()
        }


        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수 방지
        _binding = null
    }
}