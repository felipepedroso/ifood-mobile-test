package br.pedroso.tweetsentiment.domain.network.dataSources

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import io.reactivex.Observable

interface SentimentAnalysisDataSource {
    fun analyzeSentimentFromText(text: String): Observable<Sentiment>
}