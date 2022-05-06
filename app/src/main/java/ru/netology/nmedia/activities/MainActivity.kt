package ru.netology.nmedia.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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
        binding.contentEditText.setOnClickListener {
            viewModel.onAddClicked()
        }

        binding.contentEditText.requestFocus()
        window.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    )
//
//        binding.saveButton.setOnClickListener{
//            with(binding.contentEditText) {
//                val content = text.toString()
//                viewModel.onSaveClicked(content)
//            }
//        }
//
//        binding.unDoButton.setOnClickListener{
//                viewModel.onUnDoClicked()
//        }
//
//        viewModel.currentPost.observe(this) {currentPost ->
//            with(binding.contentEditText) {
//                requestFocus()
//                window.setSoftInputMode(
//                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
//                )
//
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
//
//        val postContentActivityLauncher = registerForActivityResult(
//            PostContentActivity.ResultContract
//        ) { postContent ->
//            postContent ?: return@registerForActivityResult
//            viewModel.onSaveClicked(postContent)
//        }

        viewModel.navToPostContentEvent.observe(this) { postContent ->
            val intent = Intent(this@MainActivity, PostContentActivity::class.java)
            intent.putExtra("TEXT", postContent)
            startActivityForResult(intent, 0)
//            postContentActivityLauncher.launch()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val postContent = data?.getStringExtra(
                    "ru.netology.nmedia.PostContentActivity.THIEF"
                )
                if (postContent != null) {
                    viewModel.onSaveClicked(postContent)
                }
            }
        }
    }
}
