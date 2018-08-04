package br.pedroso.tweetsentiment.domain.network.dataSources

import br.pedroso.tweetsentiment.domain.Sentiment
import io.reactivex.Observable

interface SentimentAnalysisDataSource {
    fun analyzeSentimentFromText(text: String): Observable<Sentiment>
}