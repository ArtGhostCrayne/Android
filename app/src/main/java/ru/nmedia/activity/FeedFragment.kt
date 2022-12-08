package ru.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.nmedia.R
import ru.nmedia.adapter.OnInteractionListener
import ru.nmedia.adapter.PostsAdapter
import ru.nmedia.databinding.FragmentFeedBinding
import ru.nmedia.dto.Post
import ru.nmedia.repository.PostViewModel


class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()

    private var _binding: FragmentFeedBinding? = null
    val binding: FragmentFeedBinding
        get() = _binding!!


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
        }

        override fun onSelect(post: Post) {
//            viewModel.getById(post.id)
            findNavController().navigate(
                R.id.action_feedFragment_to_postViewFragment, bundleOf(
                    PostViewFragment.ARG_ID_POST to post.id
                )
            )
        }

        override fun onEdit(post: Post) {
            viewModel.editPost(post)
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment, bundleOf(
                    NewPostFragment.ARG_CONTENT to post.content
                )
            )
        }
    }

    companion object {

        const val SAVE_CONTENT = "SAVE_CONTENT"
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)

        subscribe(binding)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

//            viewModel.add("test")
        //        INSERT INTO ${PostColumns.TABLE} VALUES
        //        ${PostColumns.COLUMN_TITLE} = "1231313",
        //        ${PostColumns.COLUMN_CONTENT} = "content",
        //        ${PostColumns.COLUMN_DATE} = "now"



//            findNavController().navigate(
//                R.id.action_feedFragment_to_postViewFragment, bundleOf(
//                    PostViewFragment.ARG_ID_POST to "3"
//                )
//            )
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subscribe(binding: FragmentFeedBinding) {
        val adapter = PostsAdapter(interactionListener)
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(adapter.currentList.size)
                }
            }

        }

    }
}
