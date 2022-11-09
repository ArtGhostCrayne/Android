package ru.nmedia.dto


data class Post(
    val id: Long,
    val title: String,
    val date: String,
    val content: String,
    val likeCount: Int = 0,
    var liked: Boolean = false,
    val repostCount: Int = 0,
    val commentCount: Int = 0,
    val viewCount: Int = 0
)


