package ru.nmedia.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.nmedia.R
import ru.nmedia.databinding.FragmentNewEditPostBinding
import ru.nmedia.repository.PostViewModel
import ru.nmedia.util.AndroidUtils


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

    @SuppressLint("CommitPrefEdits")
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


        val preferences= activity?.getSharedPreferences("user_content", 0)

        preferences?.apply{
            binding.editContentEt.setText(getString("content", null))
            edit().apply{
                clear()
                apply()
            }
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id != 0L) binding.editContentEt.setText(it.content)
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

        with(binding.editContentEt){
            requestFocus()
            AndroidUtils.showKeyboard(this)
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){

            val saveContent = binding.editContentEt.text.toString()

            preferences?.edit()?.apply{
                putString("content", saveContent)
                apply()
            }

            findNavController().navigateUp()

        }

        return binding.root
    }


}
