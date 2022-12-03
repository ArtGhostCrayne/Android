package ru.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.nmedia.R
import ru.nmedia.databinding.FragmentNewEditPostBinding
import ru.nmedia.repository.PostViewModel


class NewPostFragment : Fragment() {

    var content: String = ""


    private val viewModel: PostViewModel by activityViewModels()

    private var _binding: FragmentNewEditPostBinding? = null
    val binding: FragmentNewEditPostBinding
        get() = _binding!!

    companion object {
        const val ARG_CONTENT = "ARG_CONTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            content = it.getString(ARG_CONTENT).toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentNewEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        binding.editContentEt.setText(content)

        viewModel.edited.observe(viewLifecycleOwner) {//
            binding.editContentEt.setText(it.content)
        }


        binding.ok.setOnClickListener {
            if (binding.editContentEt.text.isNullOrBlank()) {
                Toast.makeText(
                    this.context,
                    R.string.msg_empty_post_text,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val content = binding.editContentEt.text.toString()
                with(viewModel) {
                    if (edited.value?.id != 0L) {
                        edit(viewModel.edited.value!!.copy(content = content))
                    } else {
                        add(content)
                    }
                }

                findNavController().navigateUp()
            }


        }


        return binding.root
    }


}
