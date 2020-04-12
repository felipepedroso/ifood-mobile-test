package br.pedroso.tweetsentiment.app.features.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import br.pedroso.tweetsentiment.R

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    companion object {
        fun createIntent(context: Context) = Intent(context, HomeActivity::class.java)

        fun navigateHere(context: Context) {
            context.startActivity(createIntent(context))
        }
    }
}
