package br.pedroso.tweetsentiment.domain

import java.util.*

/**
 * Created by felipe on 08/03/18.
 */
data class Tweet(
        val id: Long,
        val text: String,
        val creationTimestamp: Date,
        val sentiment: Sentiment = Sentiment.NotAnalyzed)