package ru.netology.nmedia.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapters.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.SQLiteRepository
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel(
    application: Application
): AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository =
        SQLiteRepository(
            dao = AppDb.getInstance(
                context = application
            ).postDao
        )

    val data = repository.getAll()

    val sharePostContent = SingleLiveEvent<String>()
    val navToPostViewing = SingleLiveEvent<Post?>()
    val navToPostEditContentEvent = SingleLiveEvent<String>()
    val navToFeedFragment = SingleLiveEvent<Unit>()
    val currentPost = MutableLiveData<Post?>(null)
    val sharePostVideo = SingleLiveEvent<String?>()

    fun onSaveClicked(content: String) {
        if (content.isBlank()) return
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("LOCALIZE"))
        val postToAdd = currentPost.value?.copy(
            textContent = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "New author",
            textContent = content,
            videoContent = null,
            published = (sdf.format(Date())).toString()
        )
        repository.save(postToAdd)
        currentPost.value = null
    }

    override fun onShareVideoClicked(post: Post) {
        sharePostVideo.value = post.videoContent
    }

    override fun onHeartClicked(post: Post) =
        repository.likeById(post.id)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.textContent
        repository.shareBiId(post.id)
    }

    override fun onRemoveClicked(post: Post) {
        repository.removeById(post.id)
        navToFeedFragment.call()
    }

    override fun onEditClicked(post: Post) {
        navToPostEditContentEvent.value = post.textContent
        currentPost.value = post
    }

    override fun onUnDoClicked() {
        currentPost.value = null
    }

    override fun onAddClicked() {
      navToPostEditContentEvent.call()
    }

    override fun onPostContentClicked(post: Post) {
        navToPostViewing.value = post
    }
}