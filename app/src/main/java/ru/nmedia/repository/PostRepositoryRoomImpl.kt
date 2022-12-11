package ru.nmedia.repository


import androidx.lifecycle.Transformations
import ru.nmedia.dao.PostDao
import ru.nmedia.dto.Post
import ru.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            it.toDto()
        }
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun repostById(id: Long) {
        dao.repostById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun edit(post: Post) {
        dao.edit(post.id, post.content)
    }

    override fun add(text: String) {

        dao.add(PostEntity.fromDto(Post(0, title = "Заголовок", content = text, date = "Сейчас")))

    }
}
