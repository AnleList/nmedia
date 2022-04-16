package ru.netology.nmedia.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardLayoutBinding
import ru.netology.nmedia.valueToStringForShowing

internal class PostsAdapter(
    private val onHeartClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit,
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostCardLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder (
        private val binding: PostCardLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            avatar.setImageResource(
                when (post.id) {
                    1L -> R.drawable.ic_launcher_foreground
                    2L -> R.drawable.ic_skillbox
                    else -> R.drawable.ic_baseline_tag_faces_24
                }
            )
            author.text = post.author
            published.text = post.published
            about.text = post.content
            shared.text = valueToStringForShowing(post.shared)
            views.text = valueToStringForShowing(
                when (post.id) {
                    1L -> 2999999
                    2L -> 999
                    else -> 0
                }
            )
            likes.text = valueToStringForShowing(post.likes)
            heart.setImageResource(heartChooser(post.likedByMe))
            heart.setOnClickListener { onHeartClicked(post) }
            share.setOnClickListener {
                onShareClicked(post)
            }
            share.setImageResource(
                if (post.sharedByMe) R.drawable.ic_shared_24
                else R.drawable.ic_share_24
            )
        }
        @DrawableRes
        private fun heartChooser (liked: Boolean) =
            if (liked) R.drawable.ic_red_heart_24
            else R.drawable.heart_empty
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
           oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }
}
