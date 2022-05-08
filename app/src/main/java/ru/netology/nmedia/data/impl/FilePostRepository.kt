package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class FilePostRepository(
    private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val data
    : MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(REPOSITORY_FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(REPOSITORY_FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application.openFileOutput(
                REPOSITORY_FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likes = if (!it.likedByMe) it.likes + 1 else it.likes - 1,
                likedByMe = !it.likedByMe
            )
        }
    }

    override fun shareBiId(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                sharedByMe = true,
                shared = it.shared + 1
            )
        }
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID)
            insert(post)
        else update(post)
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post
            else it
        }
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(
                id = if (posts.isEmpty()) 1L
                else (posts.first().id) + 1L
            )
        ) + posts
    }

    private companion object {
        const val REPOSITORY_FILE_NAME = "posts.json"
    }
}