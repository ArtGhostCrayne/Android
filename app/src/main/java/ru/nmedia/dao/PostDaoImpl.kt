package ru.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import ru.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {

    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_TITLE} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_DATE} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKE_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_COMMENT_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEW_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_REPOST_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO} TEXT DEFAULT "",
            ${PostColumns.COLUMN_DRAFT} BOOLEAN NOT NULL DEFAULT 0            
        );

        """.trimIndent()


    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DATE = "date"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_LIKE_COUNT = "like_count"
        const val COLUMN_LIKED = "liked"
        const val COLUMN_REPOST_COUNT = "repost_count"
        const val COLUMN_COMMENT_COUNT = "comment_count"
        const val COLUMN_VIEW_COUNT = "view_count"
        const val COLUMN_VIDEO = "video"
        const val COLUMN_DRAFT = "draft"

        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_DATE,
            COLUMN_CONTENT,
            COLUMN_LIKE_COUNT,
            COLUMN_LIKED,
            COLUMN_REPOST_COUNT,
            COLUMN_COMMENT_COUNT,
            COLUMN_VIEW_COUNT,
            COLUMN_VIDEO,
            COLUMN_DRAFT
        )
    }

    private fun mapCursor(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                title = getString(getColumnIndexOrThrow(PostColumns.COLUMN_TITLE)),
                date = getString(getColumnIndexOrThrow(PostColumns.COLUMN_DATE)),
                likeCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKE_COUNT)),
                liked = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED)) != 0,
                commentCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_COMMENT_COUNT)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                repostCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_REPOST_COUNT)),
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO)),
                viewCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEW_COUNT))
            )
        }
    }


    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_DRAFT} = 0",
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(mapCursor(it))
            }
        }
        return posts
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE ${PostColumns.TABLE} SET
               ${PostColumns.COLUMN_LIKE_COUNT} = ${PostColumns.COLUMN_LIKE_COUNT} + CASE WHEN ${PostColumns.COLUMN_LIKED} THEN -1 ELSE 1 END,
               ${PostColumns.COLUMN_LIKED} = CASE WHEN ${PostColumns.COLUMN_LIKED} THEN 0 ELSE 1 END
           WHERE ${PostColumns.COLUMN_ID} = ?;
        """.trimIndent(), arrayOf(id.toString())
        )
    }

    override fun repostById(id: Long) {
        db.execSQL(
            """
           UPDATE ${PostColumns.TABLE} SET
               ${PostColumns.COLUMN_REPOST_COUNT} = ${PostColumns.COLUMN_REPOST_COUNT} + 1
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE, "${PostColumns.COLUMN_ID} = ?", arrayOf(id.toString())
        )
    }

    override fun edit(post: Post) {
        val values = ContentValues().apply {
            put(PostColumns.COLUMN_CONTENT, post.content)
        }
        db.update(
            PostColumns.TABLE,
            values,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(post.id.toString()),
        )
    }

    override fun add(text: String) {
        val values = ContentValues().apply {
            put(PostColumns.COLUMN_TITLE, "Me")
            put(PostColumns.COLUMN_CONTENT, text)
            put(PostColumns.COLUMN_DATE, "now")
        }
        db.insert(PostColumns.TABLE, null, values)

    }

}