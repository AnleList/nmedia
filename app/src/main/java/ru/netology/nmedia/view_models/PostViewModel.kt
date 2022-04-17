package ru.netology.nmedia.view_models

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository

class PostViewModel: ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data = repository.getAll()

    fun likeById(post: Post) = repository.likeById(post.id)

    fun shareById(post: Post) = repository.shareBiId(post.id)
}