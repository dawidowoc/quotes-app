package com.github.dawidowoc.quotesapp.quotes

interface QuotesDao {
    fun saveQuotes(quotes: List<String>)
    fun getQuotes(): List<String>
}

class DummyQuotesDao : QuotesDao {
    override fun saveQuotes(quotes: List<String>) {
    }

    override fun getQuotes(): List<String> = listOf(
        "External things are not the problem. It's your assessment of them. Which you can erase right now.",
        "Memento Mori",
        "Be tolerant with others and strict with yourself",
        "Choose not to be harmed - and you won't feel harmed. Don't feel harmed - and you haven't been",
        "Don't explain your philosophy. Embody it"
    )
}