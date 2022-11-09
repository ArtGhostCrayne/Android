package ru.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nmedia.R
import com.example.nmedia.databinding.PostLayoutBinding
import ru.nmedia.countToString
import ru.nmedia.dto.Post
import ru.nmedia.repository.PostViewModel

typealias OnLikeListener = (post: Post) -> Unit
typealias OnRepostListener = (post: Post) -> Unit

class PostViewHolder(
    private val binding: PostLayoutBinding,
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            datePostTv.text = post.date
            titleTv.text = post.title
            postTextTv.text = post.content
            likesTv.text = countToString(post.likeCount)
            repostsTv.text = countToString(post.repostCount)
            commentsTv.text = countToString(post.commentCount)
            viewsTv.text = countToString(post.viewCount)
            if (post.liked) likesIv.setImageResource(R.drawable.i_liked) else likesIv.setImageResource(
                R.drawable.ic_baseline_heart_broken_24
            )

            likesIv.setOnClickListener {
                onLikeListener(post)
            }

            repostIv.setOnClickListener {
                onRepostListener(post)
            }
        }
    }
}

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onRepostListener)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}
