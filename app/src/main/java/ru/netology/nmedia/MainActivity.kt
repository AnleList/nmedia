package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.databinding.PostCardLayoutBinding
import ru.netology.nmedia.view_models.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) {posts ->
            binding.render(posts)
        }
    }

    private fun ActivityMainBinding.render(posts: List<Post>) {
        for (post in posts) PostCardLayoutBinding.inflate(
            layoutInflater, root, true
        ).render(post)
    }

    private fun PostCardLayoutBinding.render(post: Post) {
        author.text = post.author
        published.text = post.published
        about.text = post.content
        shared.text = valueToStringForShowing(post.shared)
        views.text = valueToStringForShowing(2999999)
        likes.text = valueToStringForShowing(post.likes)
        heart.setImageResource(heartChooser(post.likedByMe))
        heart.setOnClickListener { viewModel.likeById(post.id) }
        share.setOnClickListener {
            viewModel.shareById(post.id)
            share.setImageResource(R.drawable.ic_shared_24)
        }

    }

    @DrawableRes
    private fun heartChooser (liked: Boolean) =
        if (liked) R.drawable.ic_red_heart_24
        else R.drawable.heart_empty
}
