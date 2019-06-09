package br.pedroso.tweetsentiment.network.naturalLanguageApi

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import br.pedroso.tweetsentiment.domain.network.errors.NaturalLanguageApiError
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.NaturalLanguageApiService
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.Document
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.NaturalLanguageApiRequestBody
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.mappers.RetrofitResponseMapper
import io.reactivex.Observable
import io.reactivex.functions.Function

class NaturalLanguageApiDataSource(val naturalLanguageApiService: NaturalLanguageApiService) : SentimentAnalysisDataSource {
    override fun analyzeSentimentFromText(text: String): Observable<Sentiment> {
        val document = Document(text)
        val requestBody = NaturalLanguageApiRequestBody(document)
        return naturalLanguageApiService.analyzeSentiment(requestBody)
                .map { RetrofitResponseMapper.retrofitResponseToDomain(it) }
                .onErrorResumeNext(NaturalLanguageErrorMapper())
    }

    private class NaturalLanguageErrorMapper<T> : Function<Throwable, Observable<T>> {
        override fun apply(error: Throwable): Observable<T> {
            return Observable.error(NaturalLanguageApiError())
        }
    }
}