package ru.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.nmedia.R
import ru.nmedia.adapter.OnInteractionListener
import ru.nmedia.databinding.FragmentPostViewBinding
import ru.nmedia.dto.Post
import ru.nmedia.repository.PostViewModel


class PostViewFragment : Fragment() {

    private var postId: Long = 0

    private val viewModel: PostViewModel by activityViewModels()

    val interactionListener = object : OnInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onRepost(post: Post) {

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }

            val repostIntent = Intent.createChooser(intent, "Куда отправить")

            startActivity(repostIntent)

            viewModel.repostById(post.id)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            findNavController().navigateUp()
        }

        override fun onEdit(post: Post) {
            viewModel.editPost(post)
            findNavController().navigate(
                R.id.action_postViewFragment_to_newPostFragment, bundleOf(
                    NewPostFragment.ARG_CONTENT to post.content
                )
            )
        }
    }

    private var _binding: FragmentPostViewBinding? = null
    val binding: FragmentPostViewBinding
        get() = _binding!!

    companion object {
        const val ARG_ID_POST = "ARG_ID_POST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getLong(ARG_ID_POST)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPostViewBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe

            with(binding) {
                loadPost(this, post, interactionListener)

            }

        }


        return binding.root
    }


    private fun loadPost(
        binding: FragmentPostViewBinding,
        post: Post,
        onInteractionListener: OnInteractionListener
    ) {
        with(binding) {
            datePostTv.text = post.date
            titleTv.text = post.title
            postTextTv.text = post.content
            repostIv.text = countToString(post.repostCount)
            commentsIv.text = countToString(post.commentCount)
            viewsTv.text = countToString(post.viewCount)

            likesMb.text = countToString(post.likeCount)
            likesMb.isChecked = post.liked

            groupVideo.isVisible = post.video.isNotBlank()


            likesMb.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            repostIv.setOnClickListener {
                onInteractionListener.onRepost(post)
            }


            videoPlayFab.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                ContextCompat.startActivity(it.context, intent, null)
            }

            videoIv.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                ContextCompat.startActivity(it.context, intent, null)
            }



            postMenuIv.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_popup_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
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
