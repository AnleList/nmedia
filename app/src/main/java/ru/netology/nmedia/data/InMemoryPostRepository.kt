package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData

class InMemoryPostRepository: PostRepository {

    override val data = MutableLiveData(
        Post(
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
            likedByMe = false,
            likes = 99,
            sharedByMe = false,
            shared = 997,
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val likedPost = currentPost.copy(
            likes = if (!currentPost.likedByMe) currentPost.likes + 1 else currentPost.likes - 1,
            likedByMe = !currentPost.likedByMe
        )
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val sharedPost = currentPost.copy(
            sharedByMe = true,
            shared = currentPost.shared + 1
        )
        data.value = sharedPost
    }
}