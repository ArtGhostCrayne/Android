package ru.nmedia

import ru.nmedia.dto.Post
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nmedia.R
import com.example.nmedia.databinding.ActivityMainBinding
import java.text.DecimalFormat


fun countToString(count: Int): String {
    //вар 1
    val df = if (count < 10_000 || count > 1_000_000) DecimalFormat("#.#") else DecimalFormat("#")
    return when {
        count >= 1_000_000 -> df.format((count / 1_000_000.0)) + "M"
        count >= 1_000 -> df.format((count / 1_000.0)) + "K"
        else -> count.toString()
    }
    //вар 2
//    return when {
//        count in 1000..9999 -> String.format("%.1fK", count / 1000.0)
//        count in 10_000..999999 -> String.format("%dK", count / 1000)
//        count > 1_000_000 -> String.format("%.1fM", count / 1_000_000.0)
//        else -> count.toString()
//    }

}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            title = "Нетология. Меняем карьеру через образование",
            date = "Сегодня 10:00",
            commentCount = 120_500,
            repostCount = 1090,
            viewCount = 1_200_000,
            content = "В комьюнити студентов и выпускников курса «Моушн-дизайнер в 2D и 3D» прошёл конкурс по созданию заставки для площадки курса на YouTube. Главная идея ― приблизить работу над роликом к условиям реальной работы в студии, чтобы у участников было понимание настоящего процесса. Конкурс проходил с сопровождением преподавателя курса Александром Рябцевым ― бродкаст-дизайнером телеканала ТВ-3. Ролики создавались в несколько этапов:    · подбор референсов,  · создание стилфрейма,  · раскадровка будущего ролика и фотоматик,  · создание анимации.    После каждого этапа участники встречались с Александром, обсуждали работы и получали рекомендации для доработки. Студенты поделились готовыми видео. Рекомендуем открыть и посмотреть каждое. В финале Александр в качестве ролика-победителя выбрал работу Анастасии Крестиничевой. А участники комьюнити выбрали обладательницу приза зрительских симпатий ― ей стала Венера Емельянова. http://netology.ru "
        )

        with(binding) {
            datePostTv.text = post.date
            titleTv.text = post.title
            postTextTv.text = post.content
            likesTv.text = countToString(post.likeCount)
            repostsTv.text = countToString(post.repostCount)
            commentsTv.text = countToString(post.commentCount)
            viewsTv.text = countToString(post.viewCount)
            if (post.liked) binding.likesIv.setImageResource(R.drawable.i_liked)


            repostIv.setOnClickListener {
                post.repostCount += 1
                repostsTv.text = countToString(post.repostCount)
            }

            likesIv.setOnClickListener {
                post.liked = !post.liked

                if (post.liked) {
                    likesIv.setImageResource(R.drawable.i_liked)
                    post.likeCount++
                } else {
                    likesIv.setImageResource(R.drawable.ic_baseline_heart_broken_24)
                    post.likeCount--
                }
                likesTv.text = countToString(post.likeCount)

            }


        }
    }
}
