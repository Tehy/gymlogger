package com.example.gymlogger

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext


class Example(context: Context) {
    protected var context: Context

    init {
        this.context = context.getApplicationContext()
    }
}