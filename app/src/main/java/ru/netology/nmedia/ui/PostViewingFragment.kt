package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostViewingFragmentBinding
import ru.netology.nmedia.valueToStringForShowing
import ru.netology.nmedia.view_models.PostViewModel

class PostViewingFragment : Fragment() {

    private val args by navArgs<PostViewingFragmentArgs>()
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navToFeedFragment.observe(this) {
            val direction = PostViewingFragmentDirections
                .actionPostViewingFragmentToFeedFragment()
            findNavController().navigate(direction)
        }

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
        val postToViewing = args.postToViewing
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
    }.root
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        PostViewingFragmentBinding.inflate(
//            layoutInflater
//        ).also { binding ->
//            with(binding.includedPost) {
//                val postToViewing = args.postToViewing
//                postAvatar.setImageResource(
//                    when (postToViewing.author) {
//                        "Нетология. Университет интернет-профессий" ->
//                            R.drawable.ic_launcher_foreground
//                        "Skillbox. Образовательная платформа" ->
//                            R.drawable.ic_skillbox
//                        else ->
//                            R.drawable.ic_baseline_tag_faces_24
//                    }
//                )
//                postAuthor.text = postToViewing.author
//                postPublished.text = postToViewing.published
//                postTextContent.text = postToViewing.textContent
//                fabVideo.visibility = if (postToViewing.videoContent != null) {
//                    View.VISIBLE
//                } else View.GONE
//                postVideoView.visibility = if (postToViewing.videoContent != null) {
//                    View.VISIBLE
//                } else View.GONE
//                views.text = valueToStringForShowing(
//                    when (postToViewing.author) {
//                        "Нетология. Университет интернет-профессий" ->
//                            2999999
//                        "Skillbox. Образовательная платформа" ->
//                            999
//                        else ->
//                            0
//                    }
//                )
//                postHeart.text = valueToStringForShowing(postToViewing.likes)
//                postHeart.isChecked = postToViewing.likedByMe
//                share.text = valueToStringForShowing(postToViewing.shared)
//                share.isChecked = postToViewing.sharedByMe
//                postHeart.setOnClickListener { viewModel.onHeartClicked(postToViewing) }
//            }
//        }.root
//    }

//    private val popupMenu by lazy {
//        PopupMenu(itemView.context, binding.postMenuButton).apply {
//            inflate(R.menu.options_post)
//            setOnMenuItemClickListener { menuItem ->
//                when (menuItem.itemId) {
//                    R.id.removeItem -> {
//                        listener.onRemoveClicked(post)
//                        true
//                    }
//                    R.id.editItem -> {
//                        listener.onEditClicked(post)
//                        true
//                    }
//                    else -> false
//                }
//            }
//        }
//    }

}