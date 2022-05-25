package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data = dao.getAll().map {entities ->
        entities.map { it.toModel() }
    }

    override fun save(post: Post) {
        dao.save(post.toEntity())
    }

    override fun likeById(postId: Long) {
        dao.likeById(postId)
    }

    override fun shareBiId(postId: Long) {
        dao.shareBiId(postId)
    }

    override fun removeById(postId: Long) {
        dao.removeById(postId)
    }

    override fun getAll(): LiveData<List<Post>> = data
}