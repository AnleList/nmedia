package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareBiId(id: Long)
}