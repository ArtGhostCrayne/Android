package ru.nmedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.nmedia.dao.PostDao
import ru.nmedia.dao.PostDaoImpl

class DbHelper(context: Context, dbVersion: Int, dbName: String, private val DDLs: Array<String>) :
    SQLiteOpenHelper(context, dbName, null, dbVersion) {
    override fun onCreate(db: SQLiteDatabase) {
        DDLs.forEach {
            db.execSQL(it)
        }

        db.execSQL("""
        INSERT INTO ${ PostDaoImpl.PostColumns.TABLE} (${ PostDaoImpl.PostColumns.COLUMN_TITLE},${ PostDaoImpl.PostColumns.COLUMN_CONTENT},${ PostDaoImpl.PostColumns.COLUMN_DATE},${ PostDaoImpl.PostColumns.COLUMN_LIKE_COUNT})
        VALUES (
            "Нетология. Меняем карьеру через образование",
            "В комьюнити студентов и выпускников курса «Моушн-дизайнер в 2D и 3D» прошёл конкурс по созданию заставки для площадки курса на YouTube. Главная идея ― приблизить работу над роликом к условиям реальной работы в студии, чтобы у участников было понимание настоящего процесса. Конкурс проходил с сопровождением преподавателя курса Александром Рябцевым ― бродкаст-дизайнером телеканала ТВ-3. Ролики создавались в несколько этапов:    · подбор референсов,  · создание стилфрейма,  · раскадровка будущего ролика и фотоматик,  · создание анимации.    После каждого этапа участники встречались с Александром, обсуждали работы и получали рекомендации для доработки. Студенты поделились готовыми видео. Рекомендуем открыть и посмотреть каждое. В финале Александр в качестве ролика-победителя выбрал работу Анастасии Крестиничевой. А участники комьюнити выбрали обладательницу приза зрительских симпатий ― ей стала Венера Емельянова. http://netology.ru ",
            "Сегодня 10:00",
            2);
            """.trimIndent()
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}

class AppDb private constructor(db: SQLiteDatabase) {
    val postDao: PostDao = PostDaoImpl(db)

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(
                    buildDatabase(context, arrayOf(PostDaoImpl.DDL))
                ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs,
        ).writableDatabase
    }
}