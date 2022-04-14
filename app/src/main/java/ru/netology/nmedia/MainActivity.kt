package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.view_models.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val post = Post( // to delete
//            id = 1,
//            author = "Нетология. Университет интернет-профессий",
//            content = "Помогаем расти в профессии и открывать новые карьерные горизонты \uD83D\uDCAB\n" +
//                    "\n" +
//                    "Нетология — это четыре уровня образования:\n" +
//                    "\n" +
//                    "\uD83D\uDD39 Для начинающих, которые хотят освоить актуальную профессию с нуля\n" +
//                    "\uD83D\uDD39 Для специалистов, которые стремятся вырасти в карьере\n" +
//                    "\uD83D\uDD39 Для руководителей, которые нацелены усилить лидерские навыки\n" +
//                    "\uD83D\uDD39 И для компаний, которым нужно обучить команду\n" +
//                    "\n" +
//                    "Маркетинг, дизайн, программирование, soft skills, MBA, управление проектами, аналитика и Data Science — выбирайте, что вам ближе и приходите к нам за знаниями и ростом.\n" +
//                    "\n" +
//                    "Перемены — всегда правильный выбор \uD83D\uDC4C\n" +
//                    "http://netolo.gy/fEU\n" +
//                    "8 (800) 301-39-69\n" +
//                    "Варшавское шоссе, д. 1, стр. 6, 1 этаж, офис 105А, Москва",
//            published = "21 мая в 18:36",
//            likedByMe = false,
//            likes = 9999,
//            sharedByMe = false,
//            shared = 9097,
//        )

        viewModel.data.observe(this) {post ->
            binding.render(post)
        }

//        if (post.likedByMe) {
//            heart?.setImageResource(R.drawable.ic_red_heart_24)
//        }
//        if (post.sharedByMe) share?.setImageResource(R.drawable.ic_shared_24)

        heart?.setOnClickListener {
            viewModel.onHeartClicked()
            val currentPost = checkNotNull(viewModel.data.value) {
                "Data value should not be null"
            }
                heart.setImageResource(
                if (currentPost.likedByMe) R.drawable.ic_red_heart_24
                else R.drawable.heart_empty
            )
            likes.text = valueToStringForShowing(currentPost.likes)
        }

        share?.setOnClickListener {
            post.shared += 1
            shared.text = valueToStringForShowing(post.shared)
            share.setImageResource(R.drawable.ic_shared_24)
        }
    }
}

private fun ActivityMainBinding.render(post: Post) {
    author.text = post.author
    published.text = post.published
    about.text = post.content
    likes.text = valueToStringForShowing(post.likes)
    shared.text = valueToStringForShowing(post.shared)
    views.text = valueToStringForShowing(2999999)
}
