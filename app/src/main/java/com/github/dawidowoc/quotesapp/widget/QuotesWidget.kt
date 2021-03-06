package com.github.dawidowoc.quotesapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.github.dawidowoc.quotesapp.MainActivity
import com.github.dawidowoc.quotesapp.R
import com.github.dawidowoc.quotesapp.quotes.NextQuoteProvider
import com.github.dawidowoc.quotesapp.quotes.SharedPreferencesNextQuoteStateDao
import com.github.dawidowoc.quotesapp.quotes.SharedPreferencesQuotesDao

/**
 * Implementation of App Widget functionality.
 */
class QuotesWidget : AppWidgetProvider() {
    private lateinit var nextQuoteProvider: NextQuoteProvider

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val nextQuote = NextQuoteProvider(
            SharedPreferencesQuotesDao(context),
            SharedPreferencesNextQuoteStateDao(context)
        ).getNextQuote()

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, nextQuote)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    nextQuote: String
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.quotes_widget)
    views.setTextViewText(R.id.appwidget_text, nextQuote)

    setOnClickStartMainActivity(context, views)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun setOnClickStartMainActivity(
    context: Context,
    views: RemoteViews
) {
    val startMainActivityIntent = Intent(context, MainActivity::class.java)
    val startMainActivityPendingIntent =
        PendingIntent.getActivity(context, 0, startMainActivityIntent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.quotes_widget_layout, startMainActivityPendingIntent)
}
