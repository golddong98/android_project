package com.example.habitcheck.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.viewmodel.HabitViewModel
import com.example.habitcheck.databinding.FragmentHabitListBinding
import com.example.habitcheck.databinding.FragmentHomeBinding

// ... (생략)

class HabitListFragment : Fragment() {
    // ... (바인딩 및 ViewModel 선언 생략)

    //삭제
    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!


    // Adapter 선언
    private lateinit var habitAdapter: HabitListAdapter
    private val habitViewModel: HabitViewModel by activityViewModels() // ViewModel 선언


    //삭제
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. FAB 클릭 리스너 (기존 코드)
        binding.fabAddHabit.setOnClickListener {
            val action = HabitListFragmentDirections
                .actionHabitListFragmentToCreateUpdateHabitFragment()
            findNavController().navigate(action)
        }

        // 2. Adapter 초기화 및 RecyclerView 설정
        initRecyclerView()

        // 3. ViewModel의 LiveData 관찰
        observeHabits()
    }

    private fun initRecyclerView() {
        // Adapter 초기화 (클릭 핸들러 정의)
//        habitAdapter = HabitListAdapter(
//            onItemClick = { habit ->
//                // 목록 아이템 클릭 시, 해당 습관 ID를 가지고 수정 화면으로 이동
//                navigateToEditHabit(habit)
//            },
//            onCheckChange = { habit, isChecked ->
//                // 체크박스 상태 변경 시, DB에 완료 상태 업데이트
//                updateHabitCompletedStatus(habit, isChecked)
//            }
//        )

//        binding.recyclerViewHabits.apply {
//            adapter = habitAdapter
//            layoutManager = LinearLayoutManager(context)
//        }
    }

    private fun observeHabits() {
        // ViewModel에서 모든 습관 목록을 가져와 Adapter에 제출
//        habitViewModel.allHabits.observe(viewLifecycleOwner) { habits ->
//            habitAdapter.submitList(habits)
//
//            // 목록이 비었을 때 메시지 표시/숨김
//            binding.textViewNoHabits.visibility =
//                if (habits.isEmpty()) View.VISIBLE else View.GONE
//        }
    }

    private fun navigateToEditHabit(habit: HabitEntity) {
        // HabitEntity에 habitId가 있다고 가정하고 전달합니다.
        // val action = HabitListFragmentDirections
        //    .actionHabitListFragmentToCreateUpdateHabitFragment(habit.habitId)
        // findNavController().navigate(action)
    }

    private fun updateHabitCompletedStatus(habit: HabitEntity, isChecked: Boolean) {
        // 이 함수는 ViewModel에 HabitEntity의 완료 상태를 업데이트하는 로직을 호출해야 합니다.
        // 예: habitViewModel.updateCompleted(habit.habitId, isChecked)
    }

    // ... (onDestroyView 등 생략)
}