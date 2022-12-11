package ru.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val date: String,
    val content: String,
    val likeCount: Int = 0,
    var liked: Boolean = false,
    val repostCount: Int = 0,
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val video: String = ""
){

    fun toDto() = Post(id, title, date, content, likeCount,  liked,  repostCount, commentCount, viewCount, video)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.title, dto.date, dto.content, dto.likeCount, dto.liked, dto.repostCount,dto.commentCount,dto.viewCount,dto.video)

    }
}
