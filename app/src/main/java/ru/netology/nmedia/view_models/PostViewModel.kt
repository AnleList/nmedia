package ru.netology.nmedia.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapters.PostInteractionListener
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel: ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data = repository.getAll()

    val sharePostContent = SingleLiveEvent<String>()
    val navToPostContentEvent = SingleLiveEvent<Unit>()
    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveClicked(content: String) {
        if (content.isBlank()) return
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val postToAddToContentEditText = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "New author",
            content = content,
            published = (sdf.format(Date())).toString()
        )
        repository.save(postToAddToContentEditText)
        currentPost.value = null
    }

    override fun onHeartClicked(post: Post) =
        repository.likeById(post.id)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
        repository.shareBiId(post.id)
    }

    override fun onRemoveClicked(post: Post) =
        repository.removeById(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    override fun onUnDoClicked() {
        currentPost.value = null
    }

    override fun onAddClicked() {
      navToPostContentEvent.call()
    }
}