package ru.nmedia.repository
import androidx.lifecycle.LiveData
import ru.nmedia.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun repost()
}
