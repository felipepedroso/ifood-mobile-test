package br.pedroso.tweetsentiment.network.naturalLanguageApi

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import br.pedroso.tweetsentiment.domain.network.errors.NaturalLanguageApiError
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.NaturalLanguageApiService
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.Document
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request.NaturalLanguageApiRequestBody
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.mappers.RetrofitResponseMapper
import io.reactivex.Observable
import io.reactivex.Scheduler
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import kotlinx.coroutines.rx2.rxObservable

class NaturalLanguageApiDataSource(
    val workerScheduler: Scheduler,
    val naturalLanguageApiService: NaturalLanguageApiService
) : SentimentAnalysisDataSource {
    override fun analyzeSentimentFromText(text: String): Observable<Sentiment> {
        return rxObservable(workerScheduler.asCoroutineDispatcher()) {
            try {
                val document = Document(text)
                val requestBody = NaturalLanguageApiRequestBody(document)

                val response = naturalLanguageApiService.analyzeSentiment(requestBody)

                val sentiment = RetrofitResponseMapper.retrofitResponseToDomain(response)
                send(sentiment)
                close()
            } catch (exception: Exception) {
                close(NaturalLanguageApiError(exception))
            }
        }
    }
}
