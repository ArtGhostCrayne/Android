package ru.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nmedia.R
import ru.nmedia.databinding.ActivityIntentHandleBinding
import com.google.android.material.snackbar.Snackbar
import ru.nmedia.util.AndroidUtils

class IntentHandleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = ActivityIntentHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.let{
            if (it.action == Intent.ACTION_SEND){
                val text = it.getStringExtra(Intent.EXTRA_TEXT)
                if (text.isNullOrBlank()){
                    Snackbar.make(binding.root, R.string.msg_empty_post_text, Snackbar.LENGTH_LONG).setAction(android.R.string.ok){
                        finish()
                    }.show()
                }
            }
        }

    }
}
