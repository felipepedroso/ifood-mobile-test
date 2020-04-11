package br.pedroso.tweetsentiment.app.features.tweetsList

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.tweetsentiment.app.features.tweetsList.utils.TweetTimestampFormatter
import br.pedroso.tweetsentiment.app.features.tweetsList.utils.resourceColor
import br.pedroso.tweetsentiment.app.features.tweetsList.utils.resourceIcon
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet_content.buttonAnalyzeSentiment
import kotlinx.android.synthetic.main.item_tweet_content.textViewTweetContent
import kotlinx.android.synthetic.main.item_tweet_content.textViewTweetTimestamp
import kotlinx.android.synthetic.main.sentiment_chip.chipRoot
import kotlinx.android.synthetic.main.sentiment_chip.imageViewSentiment
import kotlinx.android.synthetic.main.sentiment_chip.textViewSentiment

class TweetViewHolder(
    override val containerView: View,
    private val buttonAnalyzeSentimentClickListener: (Tweet) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(tweet: Tweet) {
        with(tweet) {
            textViewTweetContent.text = text
            textViewTweetTimestamp.text =
                TweetTimestampFormatter.format(
                    containerView.context,
                    creationTimestamp
                )

            val color = ContextCompat.getColor(
                containerView.context,
                sentiment.resourceColor
            )

            val chipRootDrawable = chipRoot.background as GradientDrawable
            chipRootDrawable.setColor(color)

            textViewSentiment.text = sentiment.name

            imageViewSentiment.setImageResource(sentiment.resourceIcon)

            if (sentiment == Sentiment.NotAnalyzed) {
                buttonAnalyzeSentiment.setOnClickListener {
                    buttonAnalyzeSentimentClickListener(
                        tweet
                    )
                }
                buttonAnalyzeSentiment.visibility =
                    View.VISIBLE

                chipRoot.visibility = View.INVISIBLE
            } else {
                buttonAnalyzeSentiment.visibility =
                    View.INVISIBLE
                chipRoot.visibility = View.VISIBLE
            }
        }
    }
}