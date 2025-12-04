package com.example.habitcheck.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitcheck.data.entity.HabitEntity
import com.example.habitcheck.databinding.ItemHabitRowBinding

class HabitListAdapter(
    private val onSelectionChanged: (Int) -> Unit
) : ListAdapter<HabitEntity, HabitListAdapter.HabitViewHolder>(HabitDiffCallback()) {

    val selectedIds = mutableSetOf<Int>()

    // 2. ViewHolder: item_habit_row.xml의 뷰 요소들을 관리합니다.
    inner class HabitViewHolder(
        private val binding: ItemHabitRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

    //    val checkBox: CheckBox = itemV.findViewById(R.id.checkbox_habit_completed)

        fun bind(habit: HabitEntity) = with(binding){
            textViewHabitName.text = habit.name
            textViewHabitDescription.text = habit.description

            checkboxHabitCompleted.setOnCheckedChangeListener(null)
            checkboxHabitCompleted.isChecked = selectedIds.contains(habit.id)

            checkboxHabitCompleted.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) selectedIds.add(habit.id) else selectedIds.remove(habit.id)
                onSelectionChanged(selectedIds.size)
            }
        }
    }

    private class HabitDiffCallback : DiffUtil.ItemCallback<HabitEntity>() {
        override fun areItemsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding)
    }

    // 5. ViewHolder에 데이터를 바인딩할 때 호출됩니다.
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = getItem(position)
        holder.bind(currentHabit)
    }
}