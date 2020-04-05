package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import io.reactivex.Completable
import io.reactivex.Observable

class AnalyseTweetSentiment(
    private val sentimentAnalysisDataSource: SentimentAnalysisDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    fun execute(tweet: Tweet): Observable<Sentiment> {
        return if (tweet.sentiment != Sentiment.NotAnalyzed) {
            Observable.just(tweet.sentiment)
        } else {
            analyseTweetSentiment(tweet)
        }
    }

    private fun analyseTweetSentiment(tweet: Tweet): Observable<Sentiment> {
        return sentimentAnalysisDataSource.analyzeSentimentFromText(tweet.text)
            .flatMap { sentiment ->
                databaseDataSource.updateTweetSentiment(tweet, sentiment)
                    .andThen(Observable.just(sentiment))
            }
    }
}
