package br.pedroso.tweetsentiment.network.naturalLanguageApi

import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.NaturalLanguageApiService
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.Document
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.NaturalLanguageApiRequestBody
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.mappers.RetrofitResponseMapper
import io.reactivex.Observable

/**
 * Created by felip on 10/03/2018.
 */
class NaturalLanguageApiDataSource(val naturalLanguageApiService: NaturalLanguageApiService) : SentimentAnalysisDataSource {
    override fun analyzeSentimentFromText(text: String): Observable<Sentiment> {
        val document = Document(text)
        val requestBody = NaturalLanguageApiRequestBody(document)
        return naturalLanguageApiService.analyzeSentiment(requestBody)
                .map { RetrofitResponseMapper.retrofitResponseToDomain(it) }
    }
}