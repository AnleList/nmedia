package ru.netology.nmedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.PostCardLayoutBinding
import ru.netology.nmedia.valueToStringForShowing

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostCardLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder (
        private val binding: PostCardLayoutBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menuButton).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.removeItem -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.heart.setOnClickListener {
                listener.onHeartClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding)
            {
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
                share.setImageResource(
                    if (post.sharedByMe) R.drawable.ic_shared_24
                    else R.drawable.ic_share_24
                )
                menuButton.setOnClickListener { popupMenu.show() }
            }
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
