package br.pedroso.tweetsentiment.presentation.features.home.usecases

import android.text.TextUtils
import br.pedroso.tweetsentiment.domain.errors.UiError
import io.reactivex.Observable
import io.reactivex.Scheduler

class ValidateUsername(private val scheduler: Scheduler) {
    fun execute(username: String): Observable<String> {
        return Observable.just(username)
                .flatMap {
                    when {
                        TextUtils.isEmpty(it) -> Observable.error(UiError.EmptyField("Username"))
                        else -> Observable.just(it)
                    }
                }
    }
}