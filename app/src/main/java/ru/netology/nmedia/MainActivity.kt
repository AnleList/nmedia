package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post)

//        heart.setOnClickListener {
//            (it as ImageButton).setImageResource(R.drawable.ic_red_heart_24)
//        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            content = "Помогаем расти в профессии и открывать новые карьерные горизонты \uD83D\uDCAB\n" +
                    "\n" +
                    "Нетология — это четыре уровня образования:\n" +
                    "\n" +
                    "\uD83D\uDD39 Для начинающих, которые хотят освоить актуальную профессию с нуля\n" +
                    "\uD83D\uDD39 Для специалистов, которые стремятся вырасти в карьере\n" +
                    "\uD83D\uDD39 Для руководителей, которые нацелены усилить лидерские навыки\n" +
                    "\uD83D\uDD39 И для компаний, которым нужно обучить команду\n" +
                    "\n" +
                    "Маркетинг, дизайн, программирование, soft skills, MBA, управление проектами, аналитика и Data Science — выбирайте, что вам ближе и приходите к нам за знаниями и ростом.\n" +
                    "\n" +
                    "Перемены — всегда правильный выбор \uD83D\uDC4C\n" +
                    "http://netolo.gy/fEU\n" +
                    "8 (800) 301-39-69\n" +
                    "Варшавское шоссе, д. 1, стр. 6, 1 этаж, офис 105А, Москва",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
//            avatar.setImageResource(R.drawable.ic_launcher_foreground)
            published.text = post.published
            about.text = post.content
            if (post.likedByMe) {
                heart?.setImageResource(R.drawable.ic_red_heart_24)
            }

            heart?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                heart.setImageResource(
                    if (post.likedByMe) R.drawable.ic_red_heart_24
                else R.drawable.heart_empty
                )
            }
        }
    }
}