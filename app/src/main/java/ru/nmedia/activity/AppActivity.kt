package ru.nmedia.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import ru.nmedia.R
import ru.nmedia.activity.NewPostFragment.Companion.ARG_CONTENT
import ru.nmedia.db.SharedPreference

class AppActivity : AppCompatActivity() {

    lateinit var saveContent: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)


        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() != true) {
                return@let
            }

            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                bundleOf(ARG_CONTENT to text)
            );
        }

        checkGoogleApiAvailability()
    }

    private fun checkGoogleApiAvailability() {
        with(GoogleApiAvailability.getInstance()) {
            val code = isGooglePlayServicesAvailable(this@AppActivity)
            if (code == ConnectionResult.SUCCESS) {
                return@with
            }
            if (isUserResolvableError(code)) {
                getErrorDialog(this@AppActivity, code, 9000)?.show()
                return
            }
            Toast.makeText(this@AppActivity, R.string.google_play_unavailable, Toast.LENGTH_LONG)
                .show()
        }

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            println(it)
          //  fkocB1gSS9uNt1JdCfDXQf:APA91bFOxYSovOVUM1fhYSuj1IfLhehiyVJKly9YUga0_plt1aL0up2avdoEKOVIBntNCbs8CBWvfUCWe8TKjr-gY4RtRdqT3KVjFHyBbPREIllHOxJFooqdA_19E-UV856Q08DYtOrf
        }
    }


}
