package ru.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nmedia.R
import com.example.nmedia.databinding.ActivityMainBinding
import ru.nmedia.repository.PostViewModel
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

    lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribe()
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            binding.likesIv.setOnClickListener {
                viewModel.like()
            }

            repostIv.setOnClickListener {
                viewModel.repost()
            }
        }
    }

    private fun subscribe() {
        viewModel.data.observe(this) { post ->
            with(binding) {
                datePostTv.text = post.date
                titleTv.text = post.title
                postTextTv.text = post.content
                likesTv.text = countToString(post.likeCount)
                repostsTv.text = countToString(post.repostCount)
                commentsTv.text = countToString(post.commentCount)
                viewsTv.text = countToString(post.viewCount)
                if (post.liked) binding.likesIv.setImageResource(R.drawable.i_liked) else binding.likesIv.setImageResource(
                    R.drawable.ic_baseline_heart_broken_24
                )
            }
        }
    }
}
