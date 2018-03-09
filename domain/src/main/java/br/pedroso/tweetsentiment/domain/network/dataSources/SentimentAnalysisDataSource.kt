package br.pedroso.tweetsentiment.domain.network.dataSources

import br.pedroso.tweetsentiment.domain.Sentiment
import io.reactivex.Observable

/**
 * Created by felip on 08/03/2018.
 */
interface SentimentAnalysisDataSource {
    fun analyzeSentimentFromText(text: String): Observable<Sentiment>
}