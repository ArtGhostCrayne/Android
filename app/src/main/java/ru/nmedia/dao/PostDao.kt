package ru.nmedia.dao

import androidx.lifecycle.LiveData
import ru.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun edit(post: Post)
    fun add(text: String)
}