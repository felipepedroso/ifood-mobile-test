package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.coroutines.rx2.rxObservable

class AnalyseTweetSentiment(
    private val sentimentAnalysisDataSource: SentimentAnalysisDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    fun execute(tweet: Tweet): Observable<Sentiment> {
        return rxObservable {
            try {
                val sentiment = if(tweet.sentiment != Sentiment.NotAnalyzed) {
                    tweet.sentiment
                } else {
                    sentimentAnalysisDataSource.analyzeSentimentFromText(tweet.text).also {
                        databaseDataSource.updateTweetSentiment(tweet, it)
                    }
                }

                send(sentiment)
                close()
            } catch (exception: Exception) {
                close(exception)
            }
        }
    }
}
