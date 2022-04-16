package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {
//    val data: LiveData<Post>
//    fun like()
//    fun share()

    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareBiId(id: Long)
}