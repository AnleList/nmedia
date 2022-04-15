package ru.netology.nmedia.view_models

import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository

class PostViewModel: ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onHeartClicked() = repository.like()

    fun onShareClicked() = repository.share()
}