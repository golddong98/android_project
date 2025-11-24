package com.example.habitcheck // 👈 실제 프로젝트의 패키지 이름으로 변경

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels // 👈 Activity와 ViewModel 공유
import androidx.navigation.fragment.findNavController
import com.example.habitcheck.data.entity.HabitEntity // 👈 HabitEntity import
import com.example.habitcheck.viewmodel.HabitViewModel
import com.example.habitcheck.databinding.FragmentCreateUpdateHabitBinding

// ⚠️ ViewBinding을 사용하려면 build.gradle에 설정 필요!

class CreateUpdateHabitFragment : Fragment() {

    // ViewBinding 사용을 위한 선언 (권장 방식)
    private var _binding: FragmentCreateUpdateHabitBinding? = null
    private val binding get() = _binding!!

    // Activity 레벨의 ViewModel 공유
    private val habitViewModel: HabitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 1. 레이아웃 바인딩 설정
        _binding = FragmentCreateUpdateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. 버튼 클릭 리스너 설정
        binding.buttonSave.setOnClickListener {
            saveHabit()
        }

        binding.buttonCancel.setOnClickListener {
            // 취소 버튼 클릭 시 이전 화면으로 돌아가기
            findNavController().popBackStack()
        }

        // 3. (수정 모드 로직) 만약 기존 데이터를 수정하는 경우, 데이터를 불러와 EditText에 채웁니다.
        // 현재는 생성 로직에 집중합니다.
    }

    /** 습관 정보를 Room DB에 저장하는 함수 */
    private fun saveHabit() {
        val name = binding.editTextHabitName.text.toString().trim()
        val description = binding.editTextHabitDescription.text.toString().trim()

        if (name.isBlank()) {
            binding.inputLayoutName.error = "습관 이름을 입력해주세요."
            return
        }

        // 4. HabitEntity 객체 생성
        val newHabit = HabitEntity(
            name = name,
            description = description
            // 다른 필드들도 있다면 여기에 추가
        )

        // 5. ViewModel을 통해 DB에 삽입 (ViewModel에 insert 함수가 정의되어 있어야 합니다.)
        habitViewModel.insert(newHabit)

        // 6. 저장 후 이전 화면으로 이동
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수 방지
        _binding = null
    }
}