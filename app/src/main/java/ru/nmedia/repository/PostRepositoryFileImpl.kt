package ru.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nmedia.dto.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryFileImpl(private val context: Context) : PostRepository {
    private var lastId = 0L
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var posts = listOf(
        Post(
            id = ++lastId,
            title = "Нетология. Меняем карьеру через образование",
            date = "Сегодня 10:00",
            commentCount = 100,
            repostCount = 0,
            viewCount = 0,
            content = "В комьюнити студентов и выпускников курса «Моушн-дизайнер в 2D и 3D» прошёл конкурс по созданию заставки для площадки курса на YouTube. Главная идея ― приблизить работу над роликом к условиям реальной работы в студии, чтобы у участников было понимание настоящего процесса. Конкурс проходил с сопровождением преподавателя курса Александром Рябцевым ― бродкаст-дизайнером телеканала ТВ-3. Ролики создавались в несколько этапов:    · подбор референсов,  · создание стилфрейма,  · раскадровка будущего ролика и фотоматик,  · создание анимации.    После каждого этапа участники встречались с Александром, обсуждали работы и получали рекомендации для доработки. Студенты поделились готовыми видео. Рекомендуем открыть и посмотреть каждое. В финале Александр в качестве ролика-победителя выбрал работу Анастасии Крестиничевой. А участники комьюнити выбрали обладательницу приза зрительских симпатий ― ей стала Венера Емельянова. http://netology.ru "
        ),
        Post(
            id = ++lastId,
            title = "Нетология. Меняем карьеру через образование",
            date = "Вчера в 12:00",
            commentCount = 2000,
            repostCount = 0,
            viewCount = 0,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            content = "Если планов на неделю никаких, но чем-то интересным заняться хочется, вот подсказка: выбирайте бесплатное занятие и пробуйте себя в новом направлении. В подборке мы собрали профессии в сфере онлайн-образования и аналитики"
        ),
        Post(
            id = ++lastId,
            title = "Нетология. Меняем карьеру через образование",
            date = "Сегодня 12:00",
            commentCount = 1200000,
            repostCount = 0,
            viewCount = 0,
            content = "В комьюнити студентов и выпускников курса «Моушн-дизайнер в 2D и 3D» прошёл конкурс по созданию заставки для площадки курса на YouTube. Главная идея ― приблизить работу над роликом к условиям реальной работы в студии, чтобы у участников было понимание настоящего процесса. Конкурс проходил с сопровождением преподавателя курса Александром Рябцевым ― бродкаст-дизайнером телеканала ТВ-3. Ролики создавались в несколько этапов:    · подбор референсов,  · создание стилфрейма,  · раскадровка будущего ролика и фотоматик,  · создание анимации.    После каждого этапа участники встречались с Александром, обсуждали работы и получали рекомендации для доработки. Студенты поделились готовыми видео. Рекомендуем открыть и посмотреть каждое. В финале Александр в качестве ролика-победителя выбрал работу Анастасии Крестиничевой. А участники комьюнити выбрали обладательницу приза зрительских симпатий ― ей стала Венера Емельянова. http://netology.ru "
        )

    )
    private val data = MutableLiveData(posts)


    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else
            sync()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else {
                val cnt = if (it.liked) it.likeCount - 1 else it.likeCount + 1
                it.copy(liked = !it.liked, likeCount = cnt)
            }
        }
        data.value = posts
        sync()
    }

    override fun repostById(id: Long) {
        posts = posts.map {
            if (it.id == id) {
                it.copy(repostCount = it.repostCount + 1)
            } else it
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun edit(post: Post) {
        posts = posts.map {
            if (it.id == post.id) {
                post
            } else it
        }
        data.value = posts
        sync()
    }

    override fun add(text: String) {
        posts = posts + Post(++lastId, "Заголовок", "Только что", content = text)
        data.value = posts
        sync()
    }
}