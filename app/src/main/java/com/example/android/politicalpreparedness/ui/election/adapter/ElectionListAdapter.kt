package com.example.android.politicalpreparedness.ui.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<ElectionEntity, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ElectionViewHolder private constructor(
        private val binding: ItemElectionBinding,
        private val clickListener: ElectionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                binding.election?.let(clickListener)
            }
        }

        fun bind(election: ElectionEntity) {
            binding.election = election
            binding.executePendingBindings()
        }

        companion object {
            internal fun from(
                parent: ViewGroup,
                clickListener: ElectionListener
            ): ElectionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemElectionBinding.inflate(layoutInflater, parent, false)
                return ElectionViewHolder(binding, clickListener)
            }
        }
    }
}

object ElectionDiffCallback : ItemCallback<ElectionEntity>() {
    override fun areItemsTheSame(oldItem: ElectionEntity, newItem: ElectionEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ElectionEntity, newItem: ElectionEntity): Boolean {
        return oldItem == newItem
    }
}

typealias ElectionListener = (election: ElectionEntity) -> Unit