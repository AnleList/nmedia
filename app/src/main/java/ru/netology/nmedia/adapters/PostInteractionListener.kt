package ru.netology.nmedia.adapters

import ru.netology.nmedia.data.Post

interface PostInteractionListener {
    fun onHeartClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
}