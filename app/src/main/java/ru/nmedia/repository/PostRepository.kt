package ru.nmedia.repository

import androidx.lifecycle.LiveData
import ru.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun edit(post: Post)
    fun add(text: String)
}
