package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
//import kotlinx.android.synthetic.main.post_card_layout.*
//import kotlinx.android.synthetic.main.post_card_layout.view.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapters.PostsAdapter
import ru.netology.nmedia.databinding.PostEditContentFragmentBinding
import ru.netology.nmedia.databinding.PostViewingFragmentBinding
import ru.netology.nmedia.ui.PostEditContentFragment.Companion.REQUEST_KEY
import ru.netology.nmedia.ui.PostEditContentFragment.Companion.RESULT_KEY
import ru.netology.nmedia.valueToStringForShowing
import ru.netology.nmedia.view_models.PostViewModel

class PostViewingFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()
    private val args by navArgs<PostViewingFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostViewingFragmentBinding.inflate(
        layoutInflater, container, false
    )
//        .also { binding ->
//        val adapter = PostsAdapter(viewModel)
//    }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PostViewingFragmentBinding.inflate(
            layoutInflater
        ).also { binding ->
            with(binding.includedPost) {
                val postToViewing = args.postToViewing
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
            }
        }.root
    }

    private fun PostEditContentFragmentBinding.onSaveButtonClicked() {

        val textToSave = edit.text

        if (!textToSave.isNullOrBlank()) {
            val answerBundle = Bundle(1)
            answerBundle.putString(RESULT_KEY, textToSave.toString())
            setFragmentResult(REQUEST_KEY, answerBundle)
        }
        findNavController().popBackStack()
    }
}