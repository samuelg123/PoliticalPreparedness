package com.example.android.politicalpreparedness.ui.representative.adapter

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.ItemRepresentativeBinding
import com.example.android.politicalpreparedness.domain.entity.ChannelEntity
import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity

class RepresentativeListAdapter(private val listener: RepresentativeListener) :
    ListAdapter<RepresentativeEntity, RepresentativeViewHolder>(RepresentativeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepresentativeViewHolder {
        return RepresentativeViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class RepresentativeViewHolder private constructor(
    val binding: ItemRepresentativeBinding,
    val listener: RepresentativeListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RepresentativeEntity) {
        binding.representative = item
        binding.representativePhoto.setImageResource(R.drawable.ic_profile)

        showSocialLinks(item.official?.channels ?: emptyList())
        showWWWLinks(item.official?.urls ?: emptyList())

        binding.root.setOnClickListener {
            listener.invoke(item)
        }

        binding.executePendingBindings()
    }

    private fun showSocialLinks(channels: List<ChannelEntity>) {
        val facebookUrl = getFacebookUrl(channels)
        if (!facebookUrl.isNullOrBlank()) {
            enableLink(binding.facebookIcon, facebookUrl)
        }

        val twitterUrl = getTwitterUrl(channels)
        if (!twitterUrl.isNullOrBlank()) {
            enableLink(binding.twitterIcon, twitterUrl)
        }
    }

    private fun showWWWLinks(urls: List<String>) {
        enableLink(binding.wwwIcon, urls.first())
    }

    private fun getFacebookUrl(channels: List<ChannelEntity>): String? {
        return channels.filter { channel -> channel.type == "Facebook" }
            .map { channel -> "https://www.facebook.com/${channel.id}" }
            .firstOrNull()
    }

    private fun getTwitterUrl(channels: List<ChannelEntity>): String? {
        return channels.filter { channel -> channel.type == "Twitter" }
            .map { channel -> "https://www.twitter.com/${channel.id}" }
            .firstOrNull()
    }

    private fun enableLink(view: ImageView, url: String) {
        view.visibility = View.VISIBLE
        view.setOnClickListener { setIntent(url) }
    }

    private fun setIntent(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(ACTION_VIEW, uri)
        itemView.context.startActivity(intent)
    }

    companion object {
        fun from(parent: ViewGroup, listener: RepresentativeListener): RepresentativeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRepresentativeBinding.inflate(layoutInflater, parent, false)
            return RepresentativeViewHolder(binding, listener)
        }
    }

}

object RepresentativeDiffCallback : DiffUtil.ItemCallback<RepresentativeEntity>() {
    override fun areItemsTheSame(
        oldItem: RepresentativeEntity,
        newItem: RepresentativeEntity
    ): Boolean {
        return oldItem.entityId == newItem.entityId
    }

    override fun areContentsTheSame(
        oldItem: RepresentativeEntity,
        newItem: RepresentativeEntity
    ): Boolean {
        return oldItem == newItem
    }
}

typealias RepresentativeListener = (election: RepresentativeEntity) -> Unit