package ru.netology.nmedia.db


import ru.netology.nmedia.data.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    textContent = textContent,
    draftTextContent = draftTextContent,
    published = published,
    videoContent = videoContent,
    likes = likes,
    likedByMe = likedByMe,
    shared = shared
)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    textContent = textContent,
    draftTextContent = draftTextContent,
    published = published,
    videoContent = videoContent,
    likes = likes,
    likedByMe = likedByMe,
    shared = shared
)