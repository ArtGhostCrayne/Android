package ru.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.nmedia.databinding.ActivityNewEditPostBinding
import ru.nmedia.util.AndroidUtils

class NewEditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.edit) {
            val str = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (!str.isNullOrBlank()){
                setText(str)
            }
            requestFocus()
            AndroidUtils.showKeyboard(this)
        }



        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }

}
