package br.pedroso.tweetsentiment.features.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    companion object {
        fun createIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }
}
