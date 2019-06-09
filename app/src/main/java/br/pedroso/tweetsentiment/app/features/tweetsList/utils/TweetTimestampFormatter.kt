package br.pedroso.tweetsentiment.app.features.tweetsList.utils

import android.content.Context
import br.pedroso.tweetsentiment.R
import khronos.*
import java.text.SimpleDateFormat
import java.util.*

class TweetTimestampFormatter {
    companion object {
        private const val FORMAT_GENERIC_DATE = "dd MMM yy"
        private const val FORMAT_CURRENT_YEAR = "dd MMM"

        fun format(context: Context, timestamp: Date): String {
            val now = Dates.today

            return when (timestamp) {
                in 60.minutes.ago..now -> formatLastHourDate(context, timestamp)
                in 24.hours.ago..now -> formatLast24HoursDate(context, timestamp)
                in 1.week.ago..Dates.today -> formatLastWeekDate(context, timestamp)
                in Dates.today.beginningOfYear..Dates.today -> formatCurrentYearDate(timestamp)
                else -> formatGenericDate(timestamp)
            }
        }

        private fun formatLastHourDate(context: Context, timestamp: Date): String {
            val timespanFromNow = calculateTimeSpanFromNowInSeconds(timestamp) / 60

            return context.getString(R.string.format_last_hour_date, timespanFromNow)
        }

        private fun calculateTimeSpanFromNowInSeconds(timestamp: Date): Long {
            return (Dates.today.time - timestamp.time) / 1000
        }

        private fun formatLast24HoursDate(context: Context, timestamp: Date): String {
            val timespanFromNow = calculateTimeSpanFromNowInSeconds(timestamp) / 3600

            return context.getString(R.string.format_last_24_hours_date, timespanFromNow)
        }

        private fun formatLastWeekDate(context: Context, timestamp: Date): String {
            val timespanFromNow = calculateTimeSpanFromNowInSeconds(timestamp) / 86400

            return context.getString(R.string.format_last_week_date, timespanFromNow)
        }

        private fun formatGenericDate(timestamp: Date): String {
            val formatter = SimpleDateFormat(FORMAT_GENERIC_DATE, Locale.getDefault())
            return formatter.format(timestamp)
        }

        private fun formatCurrentYearDate(timestamp: Date): String {
            val formatter = SimpleDateFormat(FORMAT_CURRENT_YEAR, Locale.getDefault())
            return formatter.format(timestamp)
        }


    }
}