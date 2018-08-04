package br.pedroso.tweetsentiment.app.utils

import android.app.Activity
import android.support.annotation.StringRes
import android.widget.Toast

fun Activity.showToast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun Activity.showToast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()