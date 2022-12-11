package ru.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nmedia.dao.PostDao
import ru.nmedia.dto.Post

abstract class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
//    private var posts = emptyList<Post>()
//    private val data = MutableLiveData(posts)
//
//    init {
//        posts = dao.getAll()
//        data.value = posts
//    }
//
//    override fun getAll(): LiveData<List<Post>> = data
//
//
//    override fun likeById(id: Long) {
//        dao.likeById(id)
//        posts = posts.map {
//            if (it.id != id) it
//            else {
//                val cnt = if (it.liked) it.likeCount - 1 else it.likeCount + 1
//                it.copy(liked = !it.liked, likeCount = cnt)
//            }
//        }
//        data.value = posts
//
//    }
//
//    override fun repostById(id: Long) {
//        dao.repostById(id)
//        posts = posts.map {
//            if (it.id == id) {
//                it.copy(repostCount = it.repostCount + 1)
//            } else it
//        }
//        data.value = posts
//    }
//
//    override fun removeById(id: Long) {
//        dao.removeById(id)
//        posts = posts.filter { it.id != id }
//        data.value = posts
//    }
//
//    override fun edit(post: Post) {
//        dao.edit(post)
//        posts = posts.map {
//            if (it.id == post.id) {
//                post
//            } else it
//        }
//        data.value = posts
//    }
//
//    override fun add(text: String) {
//        dao.add(text)
//        posts = dao.getAll()
//        data.value = posts
//    }
}
