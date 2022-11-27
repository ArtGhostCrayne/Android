package ru.nmedia.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nmedia.dto.Post

private val empty = Post(
    id = 0,
    title = "",
    date = "",
    content = ""
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun repostById(id: Long) = repository.repostById(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun edit(post: Post) {
        repository.edit(post)
        edited.value = empty
    }

    fun editCancel() {
        edited.value = empty
    }

    fun add(text: String) = repository.add(text)

    fun editPost(post: Post) {
        edited.value = post
    }
}
