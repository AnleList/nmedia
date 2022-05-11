package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapters.PostsAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.FeedFragmetBinding
import ru.netology.nmedia.view_models.PostViewModel


class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.sharePostVideo.observe(this) { postVideoContent ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(postVideoContent))
            val shareIntent = Intent.createChooser(
                intent, getString(R.string.chooser_share_post_video)
            )
            startActivity(shareIntent)
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

        setFragmentResultListener(
            requestKey = PostEditContentFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != PostEditContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(PostEditContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveClicked(newPostContent)
        }

//        val postContentActivityLauncher = registerForActivityResult(
//            PostContentFragment.ResultContract
//        ) { postContent ->
//            postContent ?: return@registerForActivityResult
//            viewModel.onSaveClicked(postContent)
//        }
//
//        viewModel.navToPostContentEvent.observe(this) { postContent ->
//            postContentActivityLauncher.launch(postContent)
//        }

        viewModel.navToPostEditContentEvent.observe(this) { postContent ->
            val direction
                = FeedFragmentDirections.actionFeedFragmentToPostContentFragment(postContent)
            findNavController().navigate(direction
//                R.id.action_feedFragment_to_postContentFragment,
//                PostContentFragment.createBundle(postContent)
            )
        }

        viewModel.navToPostViewing.observe(this) { post ->
            val direction
                = post?.let { FeedFragmentDirections.actionFeedFragmentToPostViewingFragment(it) }
            if (direction != null) {
                findNavController().navigate(direction)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmetBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }
    }.root

    companion object {
        const val TAG = "FeedFragment"
    }

}
