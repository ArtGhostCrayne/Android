package ru.nmedia.dao

import android.content.ContentValues
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nmedia.dto.Post
import ru.nmedia.entity.PostEntity

//interface PostDao {
//    fun getAll(): List<Post>
//    fun likeById(id: Long)
//    fun repostById(id: Long)
//    fun removeById(id: Long)
//    fun edit(post: Post)
//    fun add(text: String)
//}

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Query("""
        UPDATE PostEntity SET
        likeCount = likeCount + CASE WHEN liked THEN -1 ELSE 1 END,
        liked = CASE WHEN liked THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Long)

    @Query("""
        UPDATE PostEntity SET
        repostCount = repostCount + 1
        WHERE id = :id
        """)
    fun repostById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun edit(id: Long, content: String)

    @Insert
    fun insert(post: PostEntity)

    fun add(post: PostEntity){
        insert(post)
    }
}
