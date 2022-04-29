package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.adapters.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.view_models.PostViewModel

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
            }
        }

        binding.unDoButton.setOnClickListener{
            with(binding.contentEditText) {
                viewModel.onUnDoButtonClicked()
            }
        }

        viewModel.currentPost.observe(this) {currentPost ->
            with(binding.contentEditText) {
                setText(currentPost?.content)
                if (currentPost?.content != null) {
                    requestFocus()
                    setSelection(this.text.length)
                    window.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                    )
                    showKeyboard()
                    binding.textPreview.text = currentPost.content
                    group.visibility = View.VISIBLE
                } else {
                    group.visibility = View.INVISIBLE
                    group.visibility = View.GONE
                    window.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    )
                    hideKeyboard()
                    requestFocus()
                }
            }
        }
    }
}
