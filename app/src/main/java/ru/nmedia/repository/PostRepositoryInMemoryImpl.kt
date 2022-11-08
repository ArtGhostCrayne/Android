package ru.nmedia.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nmedia.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {
    private var post = Post(
        id = 1,
        title = "Нетология. Меняем карьеру через образование",
        date = "Сегодня 10:00",
        commentCount = 0,
        repostCount = 0,
        viewCount = 0,
        content = "В комьюнити студентов и выпускников курса «Моушн-дизайнер в 2D и 3D» прошёл конкурс по созданию заставки для площадки курса на YouTube. Главная идея ― приблизить работу над роликом к условиям реальной работы в студии, чтобы у участников было понимание настоящего процесса. Конкурс проходил с сопровождением преподавателя курса Александром Рябцевым ― бродкаст-дизайнером телеканала ТВ-3. Ролики создавались в несколько этапов:    · подбор референсов,  · создание стилфрейма,  · раскадровка будущего ролика и фотоматик,  · создание анимации.    После каждого этапа участники встречались с Александром, обсуждали работы и получали рекомендации для доработки. Студенты поделились готовыми видео. Рекомендуем открыть и посмотреть каждое. В финале Александр в качестве ролика-победителя выбрал работу Анастасии Крестиничевой. А участники комьюнити выбрали обладательницу приза зрительских симпатий ― ей стала Венера Емельянова. http://netology.ru "
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        var cnt = if (post.liked) post.likeCount-1 else post.likeCount+1
        post = post.copy(liked = !post.liked, likeCount = cnt)
        data.value = post
    }

    override fun repost() {
        post = post.copy(repostCount = post.repostCount+1)
        data.value = post
    }


}
