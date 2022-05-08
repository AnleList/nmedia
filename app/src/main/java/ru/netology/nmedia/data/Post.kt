package ru.netology.nmedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val videoContent: String?,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var sharedByMe: Boolean = false,
    var shared: Int = 0,
)
