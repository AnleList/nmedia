package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.data.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.view_models.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(
            viewModel::likeById,
            viewModel::shareById,
        )
        binding.postsRecyclerview.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
    }

}
