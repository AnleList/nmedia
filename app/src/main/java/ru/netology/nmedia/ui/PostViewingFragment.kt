package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.post_card_layout.*
import kotlinx.android.synthetic.main.post_card_layout.view.*
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostEditContentFragmentBinding
import ru.netology.nmedia.databinding.PostViewingFragmentBinding
import ru.netology.nmedia.ui.PostEditContentFragment.Companion.REQUEST_KEY
import ru.netology.nmedia.ui.PostEditContentFragment.Companion.RESULT_KEY
import ru.netology.nmedia.valueToStringForShowing

class PostViewingFragment : Fragment() {

        private val args by navArgs<PostViewingFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostViewingFragmentBinding.inflate(
        layoutInflater, container, false
    ).also {
            binding ->
        with(binding) {
            val postToViewing = args.postToViewing

            post_avatar?.setImageResource(
                when (postToViewing.author) {
                    "Нетология. Университет интернет-профессий" ->
                        R.drawable.ic_launcher_foreground
                    "Skillbox. Образовательная платформа" ->
                        R.drawable.ic_skillbox
                    else ->
                        R.drawable.ic_baseline_tag_faces_24
                }
            )
            post_author?.text = postToViewing.author
            post_published?.text = postToViewing.published
            post_text_content?.text = postToViewing.textContent
            fab_video?.visibility = if (postToViewing.videoContent != null) {
                View.VISIBLE
            } else View.GONE
            post_video_view?.visibility = if (postToViewing.videoContent != null) {
                View.VISIBLE
            } else View.GONE
            views?.text = valueToStringForShowing(
                when (postToViewing.author) {
                    "Нетология. Университет интернет-профессий" ->
                        2999999
                    "Skillbox. Образовательная платформа" ->
                        999
                    else ->
                        0
                }
            )
            post_heart?.text = valueToStringForShowing(postToViewing.likes)
            post_heart?.isChecked = postToViewing.likedByMe
            share?.text = valueToStringForShowing(postToViewing.shared)
            share?.isChecked = postToViewing.sharedByMe
}
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postToViewing = args.postToViewing
        post_author?.text = postToViewing.author

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