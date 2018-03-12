package br.pedroso.tweetsentiment.app.base

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import br.pedroso.tweetsentiment.R

/**
 * Created by felip on 12/03/2018.
 */
open class BaseActivity : AppCompatActivity() {

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}