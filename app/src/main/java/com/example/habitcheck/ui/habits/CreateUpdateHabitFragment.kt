package com.example.habitcheck.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.habitcheck.data.HabitDatabase
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.data.repository.HabitRepository
import com.example.habitcheck.databinding.FragmentCreateUpdateHabitBinding
import com.example.habitcheck.viewmodel.HabitViewModel
import com.example.habitcheck.viewmodel.HabitViewModelFactory

class CreateUpdateHabitFragment : Fragment() {

    private var _binding: FragmentCreateUpdateHabitBinding? = null
    private val binding get() = _binding!!

    //private val habitViewModel: HabitViewModel by activityViewModels()
    private val habitViewModel: HabitViewModel by viewModels {
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

        binding.saveButton.isEnabled = false
        binding.name.doAfterTextChanged {
            text -> binding.saveButton.isEnabled = !text.isNullOrBlank()
        }

        binding.saveButton.setOnClickListener {
            val name = binding.name.text.toString()
            val description = binding.description.text.toString().ifEmpty { null }
            habitViewModel.saveHabit(name, description)

            findNavController().popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        //수정 데이
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수 방지
        _binding = null
    }
}