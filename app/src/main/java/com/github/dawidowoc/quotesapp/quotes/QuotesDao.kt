package com.github.dawidowoc.quotesapp.quotes

import android.content.Context

interface QuotesDao {
    fun saveQuotes(quotes: List<String>)
    fun getQuotes(): List<String>
}

class SharedPreferencesQuotesDao(private val context: Context) : QuotesDao {
    private val maxCharsInQuote = 500
    private val maxQuotes = 1000

    private val defaultQuotes =
        "External things are not the problem. It's your assessment of them. Which you can erase right now.\n" +
                "Memento Mori\n" +
                "Be tolerant with others and strict with yourself\n" +
                "Choose not to be harmed - and you won't feel harmed. Don't feel harmed - and you haven't been\n" +
                "Don't explain your philosophy. Embody it"

    override fun saveQuotes(quotes: List<String>) {
        with(getSharedPreferences().edit()) {
            putString("quotes", serializeQuotes(formatInput(quotes)))
            apply()
        }
    }

    private fun formatInput(quotes: List<String>): List<String> {
        val formattedQuotes = quotes
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { if (it.length > maxCharsInQuote) it.substring(0, maxCharsInQuote) else it }

        return formattedQuotes.subList(
            0,
            if (formattedQuotes.size > maxQuotes) maxQuotes else formattedQuotes.size
        )
    }

    override fun getQuotes(): List<String> {
        return deserializeQuotes(getSharedPreferences().getString("quotes", defaultQuotes))
    }

    private fun getSharedPreferences() =
        context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}

fun serializeQuotes(quotes: List<String>): String =
    if (quotes.isEmpty()) "" else quotes.reduce { acc, s -> acc + "\n" + s }

fun deserializeQuotes(quotes: String?): List<String> {
    if (quotes.isNullOrEmpty()) {
        return listOf()
    }
    return quotes.split("\n")
}
