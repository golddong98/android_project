package com.example.habitcheck.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitcheck.data.entity.HabitEntity // 👈 HabitEntity 클래스 경로 확인
import com.example.habitcheck.databinding.ItemHabitRowBinding // 👈 View Binding 경로 확인

// 1. ListAdapter 사용: 데이터 변경 시 효율적인 업데이트를 위해 DiffUtil을 사용합니다.
class HabitListAdapter(
    private val onItemClick: (HabitEntity) -> Unit, // 클릭 이벤트 핸들러 (수정 시 사용)
    private val onCheckChange: (HabitEntity, Boolean) -> Unit // 체크박스 상태 변경 핸들러
) : ListAdapter<HabitEntity, HabitListAdapter.HabitViewHolder>(HabitDiffCallback()) {

    // 2. ViewHolder: item_habit_row.xml의 뷰 요소들을 관리합니다.
    class HabitViewHolder(
        private val binding: ItemHabitRowBinding,
        private val onItemClick: (HabitEntity) -> Unit,
        private val onCheckChange: (HabitEntity, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: HabitEntity) {
            binding.apply {
                // 데이터 바인딩
                textViewHabitName.text = habit.name
                textViewHabitDescription.text = habit.description ?: "설명 없음"

                // ⚠️ 주의: HabitEntity에 'completed' 필드가 있다고 가정합니다.
                // 만약 completed 필드가 없다면, 이 부분을 주석 처리하거나 HabitEntity를 업데이트해야 합니다.
                // checkboxHabitCompleted.isChecked = habit.completed

                // 아이템 전체 클릭 리스너 (습관 수정 화면으로 이동)
                root.setOnClickListener {
                    onItemClick(habit)
                }

                // 체크박스 상태 변경 리스너
                checkboxHabitCompleted.setOnCheckedChangeListener { _, isChecked ->
                    onCheckChange(habit, isChecked)
                }
            }
        }
    }

    // 3. DiffUtil.ItemCallback: 목록 업데이트 시 변경된 아이템만 효율적으로 갱신합니다.
    private class HabitDiffCallback : DiffUtil.ItemCallback<HabitEntity>() {
        override fun areItemsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean {
            // ⚠️ HabitEntity의 고유 ID 필드로 비교해야 합니다.
            // 예를 들어 habitId가 있다면: return oldItem.habitId == newItem.habitId
            // 여기서는 임시로 name으로 비교합니다.
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean {
            return oldItem == newItem
        }
    }

    // 4. 새로운 ViewHolder를 생성할 때 호출됩니다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding, onItemClick, onCheckChange)
    }

    // 5. ViewHolder에 데이터를 바인딩할 때 호출됩니다.
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = getItem(position)
        holder.bind(currentHabit)
    }
}