package ru.netology.nmedia.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapters.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
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
        binding.saveButton.setOnClickListener {
            viewModel.onAddClicked()
        }

//        binding.saveButton.setOnClickListener{
//            with(binding.contentEditText) {
//                val content = text.toString()
//                viewModel.onSaveClicked(content)
//            }
//        }

        binding.unDoButton.setOnClickListener{
                viewModel.onUnDoClicked()
        }

//        viewModel.currentPost.observe(this) {currentPost ->
//            with(binding.contentEditText) {
//                setText(currentPost?.content)
//                if (currentPost?.content != null) {
//                    requestFocus()
//                    setSelection(this.text.length)
//                    window.setSoftInputMode(
//                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
//                    )
//                    showKeyboard()
//                    binding.textPreview.text = currentPost.content
//                    group.visibility = View.VISIBLE
//                } else {
//                    group.visibility = View.GONE
//                    window.setSoftInputMode(
//                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
//                    )
//                    hideKeyboard()
//                    requestFocus()
//                }
//            }
//        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(
                intent, getString(R.string.chooser_share_post)
            )
            startActivity(shareIntent)
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveClicked(postContent)
        }
        viewModel.navToPostContentEvent.observe(this) {
            postContentActivityLauncher.launch()
        }
    }
}
