package ru.netology.nmedia.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
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
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

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

        viewModel.navToPostContentEvent.observe(this) { postContent ->
            val intentToEdit = Intent(
                this@MainActivity,
                PostContentActivity::class.java
            )
            intentToEdit.putExtra(
                "ru.netology.nmedia.PostContentActivity.TEXT_TO_EDIT",
                postContent
            )
//            startActivityForResult(intentToEdit, 0)
//            startActivity(intentToEdit)
            postContentActivityLauncher.launch(postContent)

        }
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 0) {
//            if (resultCode == Activity.RESULT_OK) {
//                val postContent = data?.getStringExtra(
//                    "ru.netology.nmedia.PostContentActivity.answerIntent"
//                )
//                if (postContent != null) {
//                    viewModel.onSaveClicked(postContent)
//                }
//            }
//        }
//    }
}
