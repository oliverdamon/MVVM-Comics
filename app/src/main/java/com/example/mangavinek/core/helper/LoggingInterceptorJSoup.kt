package com.example.mangavinek.core.helper

import org.jsoup.Connection
import org.jsoup.select.Elements
import timber.log.Timber

fun Connection.Response.loggingInterceptorJSoup() {
    Timber.i("SOUP - Status Code: ${this.statusCode()}")
    Timber.i("SOUP - Status Message: ${this.statusMessage()}")
    Timber.i("SOUP - Content Type: ${this.contentType()}")
}

fun Elements.loggingInterceptorJSoupElement(): Elements {
    Timber.i("SOUP - HTML: ${this}")
    return this
}