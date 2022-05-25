package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data = dao.getAll().map {entities ->
        entities.map { it.toModel() }
    }

    init {
        val postNetology = Post(
            id = 3L,
            author = "Нетология. Университет интернет-профессий",
            textContent = "Помогаем расти в профессии и открывать новые карьерные горизонты \uD83D\uDCAB\n" +
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
            draftTextContent = null,
            videoContent = "https://youtu.be/xOgT2qYAzds",
            published = "21 мая 2020",
            likedByMe = true,
            likes = 1100,
            sharedByMe = false,
            shared = 999997,
        )
        if(data.value?.isEmpty() == true || data.value == null) {
            dao.insert(postNetology.toEntity())
        }
    }

    override fun save(post: Post) {
        dao.save(post.toEntity())
    }

    override fun likeById(postId: Long) {
        dao.likeById(postId)
    }

    override fun shareBiId(postId: Long) {
        dao.shareBiId(postId)
    }

    override fun removeById(postId: Long) {
        dao.removeById(postId)
    }

    override fun getAll(): LiveData<List<Post>> = data
}