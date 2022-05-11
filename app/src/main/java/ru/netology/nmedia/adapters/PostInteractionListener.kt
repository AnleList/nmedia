package ru.netology.nmedia.adapters

import ru.netology.nmedia.data.Post

interface PostInteractionListener {
    fun onHeartClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onUnDoClicked()
    fun onAddClicked()
    fun onShareVideoClicked(post: Post)
    fun onPostContentClicked(post: Post)
}