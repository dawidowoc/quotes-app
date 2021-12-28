package com.github.dawidowoc.quotesapp.quotes

import android.content.Context

class NextQuoteProvider(
    private val quotesDao: QuotesDao,
    private val nextQuoteStateDao: NextQuoteStateDao
) {

    fun getNextQuote(): String {
        var nextId = nextQuoteStateDao.getCurrentQuoteId() + 1
        val quotes = quotesDao.getQuotes()

        if (quotes.isEmpty()) {
            return "Add a quote"
        }

        if (nextId >= quotes.size) {
            nextId = 0
        }

        val nextQuote = quotesDao.getQuotes()[nextId]

        nextQuoteStateDao.saveCurrentQuoteId(nextId)
        return nextQuote
    }
}

interface NextQuoteStateDao {
    fun getCurrentQuoteId(): Int
    fun saveCurrentQuoteId(id: Int)
}

class SharedPreferencesNextQuoteStateDao(private val context: Context) : NextQuoteStateDao {

    override fun getCurrentQuoteId(): Int {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        return preferences.getInt("current.quote.id", -1)
    }

    override fun saveCurrentQuoteId(id: Int) {
        val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        with(preferences.edit()) {
            putInt("current.quote.id", id)
            apply()
        }
    }

}