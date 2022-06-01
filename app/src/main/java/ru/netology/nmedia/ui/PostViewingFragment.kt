package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.PostViewingFragmentBinding
import ru.netology.nmedia.valueToStringForShowing
import ru.netology.nmedia.view_models.PostViewModel

class PostViewingFragment : Fragment() {

    private val args by navArgs<PostViewingFragmentArgs>()
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navToPostEditContentEvent.observe(this) { postContent ->
            val direction = PostViewingFragmentDirections
                .actionPostViewingFragmentToPostContentFragment(postContent)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostViewingFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
//
//        setFragmentResultListener(
//            requestKey = PostEditContentFragment.REQUEST_KEY
//        ) { requestKey, bundle ->
//            if (requestKey != PostEditContentFragment.REQUEST_KEY) return@setFragmentResultListener
//            val newPostContent = bundle.getString(PostEditContentFragment.RESULT_KEY
//            ) ?: return@setFragmentResultListener
//            viewModel.onSaveClicked(newPostContent)
//        }
        var postToViewing: Post = args.postToViewing

        viewModel.data.observe(viewLifecycleOwner) {posts ->
            postToViewing = posts.first {it.id == postToViewing.id}
            with(binding.includedPost) {
                postAvatar.setImageResource(
                    when (postToViewing.author) {
                        "Нетология. Университет интернет-профессий" ->
                            R.drawable.ic_launcher_foreground
                        "Skillbox. Образовательная платформа" ->
                            R.drawable.ic_skillbox
                        else ->
                            R.drawable.ic_baseline_tag_faces_24
                    }
                )
                postAuthor.text = postToViewing.author
                postPublished.text = postToViewing.published
                postTextContent.text = postToViewing.textContent
                fabVideo.visibility = if (postToViewing.videoContent != null) {
                    View.VISIBLE
                } else View.GONE
                postVideoView.visibility = if (postToViewing.videoContent != null) {
                    View.VISIBLE
                } else View.GONE
                views.text = valueToStringForShowing(
                    when (postToViewing.author) {
                        "Нетология. Университет интернет-профессий" ->
                            2999999
                        "Skillbox. Образовательная платформа" ->
                            999
                        else ->
                            0
                    }
                )
                postHeart.text = valueToStringForShowing(postToViewing.likes)
                postHeart.isChecked = postToViewing.likedByMe
                share.text = valueToStringForShowing(postToViewing.shared)
                share.isChecked = postToViewing.sharedByMe
                postHeart.setOnClickListener { viewModel.onHeartClicked(postToViewing) }
            }
            val popupMenu by lazy {
                PopupMenu(requireContext(), binding.includedPost.postMenuButton).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.removeItem -> {
                                viewModel.onRemoveClicked(postToViewing)
                                true
                            }
                            R.id.editItem -> {
                                viewModel.onEditClicked(postToViewing)
                                true
                            }
                            else -> false
                        }
                    }
                }
            }
            binding.includedPost.postMenuButton.setOnClickListener {
                popupMenu.show()
            }

            binding.includedPost.postHeart.setOnClickListener {
                viewModel.onHeartClicked(postToViewing)
            }
            binding.includedPost.share.setOnClickListener {
                viewModel.onShareClicked(postToViewing)
            }
            binding.includedPost.fabVideo.setOnClickListener {
                viewModel.onShareVideoClicked(postToViewing)
            }
            binding.includedPost.postVideoView.setOnClickListener {
                viewModel.onShareVideoClicked(postToViewing)
            }
            binding.includedPost.postTextContent.setOnClickListener {
                viewModel.onEditClicked(postToViewing)
            }
        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
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
    }.root

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }
}