package com.example.clientapp.presentation.ui.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.R
import com.example.clientapp.databinding.OperationItemBinding
import com.example.serverapp.OperationDto


class OperationAdapter(private val viewModel: HomeViewModel) :
    ListAdapter<OperationDto, OperationAdapter.ViewHolder>(
        TaskDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
        if(item.result.equals("pending")){
            holder.binding.envolopeCard.setBackgroundColor(ContextCompat.getColor(holder.binding.envolopeCard.context,R.color.teal_200))
        }else{
            holder.binding.envolopeCard.setBackgroundColor(ContextCompat.getColor(holder.binding.envolopeCard.context,R.color.white))

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: OperationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: HomeViewModel, item: OperationDto) {
            binding.viewmodel = viewModel
            binding.operationDto = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OperationItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallback : DiffUtil.ItemCallback<OperationDto>() {
    override fun areItemsTheSame(oldItem: OperationDto, newItem: OperationDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OperationDto, newItem: OperationDto): Boolean {
        return oldItem.firstNum == newItem.firstNum &&oldItem.secNum == newItem.secNum &&
                oldItem.id == newItem.id &&oldItem.result == newItem.result &&
                oldItem.type == newItem.type
    }
}
