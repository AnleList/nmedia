package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PostRepository {

    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareBiId(id: Long)
    fun removeById(id: Long)
}