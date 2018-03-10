package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by felip on 10/03/2018.
 */
class AnalyseTweetSentiment(private val scheduler: Scheduler,
                            private val sentimentAnalysisDataSource: SentimentAnalysisDataSource,
                            private val databaseDataSource: DatabaseDataSource) {

    fun execute(tweet: Tweet): Observable<Sentiment> {
        return Observable.just(tweet.sentiment)
                .flatMap {
                    when (it) {
                        Sentiment.NotAnalyzed -> analyseTweetSentiment(tweet)
                        else -> Observable.just(it)
                    }
                }
    }

    private fun analyseTweetSentiment(tweet: Tweet): Observable<Sentiment> {
        return sentimentAnalysisDataSource.analyzeSentimentFromText(tweet.text)
                .subscribeOn(scheduler)
                .doOnNext { updateTweetSentimentField(tweet, it) }
    }

    private fun updateTweetSentimentField(tweet: Tweet, sentiment: Sentiment) {
        Completable.fromAction { databaseDataSource.updateTweetSentiment(tweet, sentiment) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe()
    }
}