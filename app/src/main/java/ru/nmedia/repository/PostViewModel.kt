package ru.nmedia.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nmedia.db.AppDb
import ru.nmedia.dto.Post

private val empty = Post(
    id = 0,
    title = "",
    date = "",
    content = ""
)

class PostViewModel(app: Application) : AndroidViewModel(app) {
    //    private val repository: PostRepository = PostRepositoryInMemoryImpl()
//    private val repository: PostRepository = PostRepositoryFileImpl(app)
    private val repository: PostRepository =
        PostRepositorySQLiteImpl(AppDb.getInstance(app).postDao)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)

    fun repostById(id: Long) = repository.repostById(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun edit(post: Post) {
        repository.edit(post)
        edited.value = empty
    }

//    fun editCancel() {
//        edited.value = empty
//    }

    fun editedClear() {
        edited.value = empty
    }

    fun add(text: String) = repository.add(text)

    fun editPost(post: Post) {
        edited.value = post
    }
}
