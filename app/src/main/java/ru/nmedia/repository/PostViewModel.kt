package ru.nmedia.repository

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private  val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun repost() = repository.repost()
}
