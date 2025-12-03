package com.example.habitcheck.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.habitcheck.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. '시작하기' 버튼 클릭 리스너 설정
        binding.buttonStart.setOnClickListener {
            // nav_graph에 정의된 action을 사용하여 HabitListFragment로 이동
            val action = HomeFragmentDirections.actionHomeFragmentToHabitListFragment()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}