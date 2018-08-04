package br.pedroso.tweetsentiment.app.features.tweetsList

import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.tweetsList.utils.TweetTimestampFormatter
import br.pedroso.tweetsentiment.app.tweetsList.utils.resourceColor
import br.pedroso.tweetsentiment.app.tweetsList.utils.resourceIcon
import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.domain.Tweet
import kotlinx.android.synthetic.main.item_tweet_content.view.*
import kotlinx.android.synthetic.main.sentiment_chip.view.*

class TweetsListAdapter(private val buttonAnalyzeSentimentClickListener: (Tweet) -> Unit) : RecyclerView.Adapter<TweetsListAdapter.TweetViewHolder>() {
    private val tweetsList: MutableList<Tweet> = ArrayList()

    fun setTweetsList(tweetsList: List<Tweet>){
        this.tweetsList.clear()
        this.tweetsList.addAll(tweetsList)
        this.tweetsList.sortByDescending { it.creationTimestamp }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TweetViewHolder?, position: Int) {
        val tweet = tweetsList[position]
        holder?.bind(tweet, buttonAnalyzeSentimentClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val layout = R.layout.item_tweet
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layout, parent, false)
        return TweetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tweetsList.size
    }

    class TweetViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tweet: Tweet, buttonAnalyzeSentimentClickListener: (Tweet) -> Unit) {
            with(tweet) {
                view.textViewTweetContent.text = text
                view.textViewTweetTimestamp.text = TweetTimestampFormatter.format(view.context, creationTimestamp)

                val color = ContextCompat.getColor(view.context, sentiment.resourceColor)

                val chipRootDrawable = view.chipRoot.background as GradientDrawable
                chipRootDrawable.setColor(color)

                view.textViewSentiment.text = sentiment.name

                view.imageViewSentiment.setImageResource(sentiment.resourceIcon)

                if (sentiment == Sentiment.NotAnalyzed) {
                    view.buttonAnalyzeSentiment.setOnClickListener { buttonAnalyzeSentimentClickListener(tweet) }
                    view.buttonAnalyzeSentiment.visibility = View.VISIBLE

                    view.chipRoot.visibility = View.INVISIBLE
                } else {
                    view.buttonAnalyzeSentiment.visibility = View.INVISIBLE
                    view.chipRoot.visibility = View.VISIBLE
                }
            }
        }
    }
}