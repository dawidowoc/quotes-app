package com.github.dawidowoc.quotesapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.github.dawidowoc.quotesapp.debug.DebugModeService
import com.github.dawidowoc.quotesapp.quotes.SharedPreferencesQuotesDao
import com.github.dawidowoc.quotesapp.quotes.deserializeQuotes
import com.github.dawidowoc.quotesapp.quotes.serializeQuotes
import com.github.dawidowoc.quotesapp.widget.WidgetUpdater

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (DebugModeService().isDebugMode(this)) {
            val triggerWidgetUpdateButton =
                findViewById<Button>(R.id.test_trigger_widget_update_button)
            triggerWidgetUpdateButton.visibility = View.VISIBLE
            triggerWidgetUpdateButton.setOnClickListener { WidgetUpdater(this).update() }
        }

        val quotesList = findViewById<EditText>(R.id.quotes_list)
        // For some reason this does not work when set in XML
        quotesList.setHorizontallyScrolling(true)
        quotesList.setText(serializeQuotes(SharedPreferencesQuotesDao(this).getQuotes()))
        quotesList.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                SharedPreferencesQuotesDao(this@MainActivity).saveQuotes(deserializeQuotes(s.toString()))
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        findViewById<EditText>(R.id.quotes_list).setText(
            serializeQuotes(
                SharedPreferencesQuotesDao(
                    this
                ).getQuotes()
            )
        )
    }
}