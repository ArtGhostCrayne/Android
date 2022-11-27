package ru.nmedia.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nmedia.R
import ru.nmedia.databinding.PostLayoutBinding
import ru.nmedia.activity.countToString
import ru.nmedia.dto.Post

//typealias OnLikeListener = (post: Post) -> Unit
//typealias OnRepostListener = (post: Post) -> Unit
//typealias OnRemoveListener= (post: Post) -> Unit

interface OnInteractionListener{
    fun onLike(post: Post) {}
    fun onRepost(post: Post) {}
    fun onRemove(post: Post) {}
    fun onEdit(post: Post) {}
}

class PostViewHolder(
    private val binding: PostLayoutBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            datePostTv.text = post.date
            titleTv.text = post.title
            postTextTv.text = post.content
//            likesTv.text = countToString(post.likeCount)
            repostIv.text = countToString(post.repostCount)
            commentsIv.text = countToString(post.commentCount)
            viewsTv.text = countToString(post.viewCount)
//            if (post.liked) likesIv.setImageResource(R.drawable.i_liked) else likesIv.setImageResource(
//                R.drawable.ic_baseline_heart_broken_24
//            )
            likesMb.text = countToString(post.likeCount)
            likesMb.isChecked = post.liked

            groupVideo.isVisible= post.video.isNotBlank()


            likesMb.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            repostIv.setOnClickListener {
                onInteractionListener.onRepost(post)
            }


            videoPlayFab.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(it.context, intent,null)
            }

            videoIv.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(it.context, intent,null)
            }



            postMenuIv.setOnClickListener{
                PopupMenu(it.context,it).apply {
                    inflate(R.menu.post_popup_menu)
                    setOnMenuItemClickListener {
                        item ->
                        when (item.itemId){
                            R.id.removePostPm -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.editPostPm -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

        }
    }
}

class PostsAdapter(
//    private val onLikeListener: OnLikeListener,
//    private val onRepostListener: OnRepostListener,
//    private val onRemoveListener: OnRemoveListener,
    private val onInteractionListener: OnInteractionListener
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)

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
