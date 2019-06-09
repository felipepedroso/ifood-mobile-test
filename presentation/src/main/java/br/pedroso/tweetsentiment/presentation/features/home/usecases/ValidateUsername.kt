package br.pedroso.tweetsentiment.presentation.features.home.usecases

import android.text.TextUtils
import br.pedroso.tweetsentiment.domain.errors.UiError
import io.reactivex.Observable

class ValidateUsername {
    fun execute(username: String): Observable<String> {
        return Observable.just(username)
                .flatMap {
                    when {
                        TextUtils.isEmpty(it) -> Observable.error(UiError.EmptyField())
                        else -> Observable.just(it)
                    }
                }
    }
}