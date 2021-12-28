package com.github.dawidowoc.quotesapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.dawidowoc.quotesapp.debug.DebugModeService
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
    }
}