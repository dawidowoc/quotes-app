package com.github.dawidowoc.quotesapp.quotes

import android.content.Context

interface QuotesDao {
    fun saveQuotes(quotes: List<String>)
    fun getQuotes(): List<String>
}

class SharedPreferencesQuotesDao(private val context: Context) : QuotesDao {

    private val defaultQuotes =
        "External things are not the problem. It's your assessment of them. Which you can erase right now.\n" +
                "Memento Mori\n" +
                "Be tolerant with others and strict with yourself\n" +
                "Choose not to be harmed - and you won't feel harmed. Don't feel harmed - and you haven't been\n" +
                "Don't explain your philosophy. Embody it"

    override fun saveQuotes(quotes: List<String>) {
        with(getSharedPreferences().edit()) {
            putString("quotes", serializeQuotes(quotes))
            apply()
        }
    }

    override fun getQuotes(): List<String> {
        return deserializeQuotes(getSharedPreferences().getString("quotes", defaultQuotes))
    }

    private fun getSharedPreferences() =
        context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}

fun serializeQuotes(quotes: List<String>): String = quotes.reduce { acc, s -> acc + "\n" + s }

fun deserializeQuotes(quotes: String?): List<String> = quotes?.split("\n") ?: listOf()
