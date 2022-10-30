package ru.nmedia.dto


data class Post(
    val id: Int,
    val title: String,
    var date: String,
    var content: String,
    var likeCount: Int = 0,
    var liked: Boolean = false,
    var repostCount: Int = 0,
    var commentCount: Int = 0,
    var viewCount: Int = 0
)


