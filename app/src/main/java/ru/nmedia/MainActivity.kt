package ru.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nmedia.R
import com.example.nmedia.databinding.ActivityMainBinding
import ru.nmedia.adapter.OnInteractionListener
import ru.nmedia.adapter.PostsAdapter
import ru.nmedia.dto.Post
import ru.nmedia.repository.PostViewModel
import ru.nmedia.util.AndroidUtils
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

    val interactionListener = object : OnInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onRepost(post: Post) {
            viewModel.repostById(post.id)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }

        override fun onEdit(post: Post) {
            viewModel.editPost(post)
        }
    }

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //    lateinit var PostLayoutBinding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribe()

        binding.editCancelIb.setOnClickListener {
            binding.layoutEditInfoCl.visibility = View.GONE
            viewModel.editCancel()
            with(binding.textInputEt) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }

        }

        binding.postApplyIb.setOnClickListener {
            with(binding.textInputEt) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.msg_empty_post_text,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (viewModel.edited.value?.id == 0L) {
                        viewModel.add(text.toString())
                    }
                    else{
                        viewModel.edit( viewModel.edited.value!!.copy(content = text.toString()))
                    }
                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }

    }

    private fun subscribe() {
        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)

        }

        viewModel.edited.observe(this){
            post ->
            if (post.id !=0L){
                with (binding.textInputEt){
                    requestFocus()
                    setText(post.content)
                }
                binding.layoutEditInfoCl.visibility = View.VISIBLE
                binding.editedPostTitleTv.text = post.title
                AndroidUtils.showKeyboard(binding.textInputEt)
            }else binding.layoutEditInfoCl.visibility = View.GONE


        }
    }
}

