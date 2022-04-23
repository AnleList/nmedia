package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.adapters.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.view_models.PostViewModel
import androidx.activity.viewModels
import ru.netology.nmedia.util.hideKeyboard


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
        binding.saveButton.setOnClickListener{
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)

                clearFocus()
                hideKeyboard()
            }
        }
        viewModel.currentPost.observe(this) {currentPost ->
            binding.contentEditText.setText(currentPost?.content)
        }
    }

}
