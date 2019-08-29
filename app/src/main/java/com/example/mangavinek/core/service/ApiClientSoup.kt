package com.example.mangavinek.core.service

import com.example.mangavinek.BuildConfig
import com.example.mangavinek.core.helper.loggingJSoup
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object JSoupService {

    fun getApiClient(url: String): Document {
        val response = Jsoup.connect(BuildConfig.LOGIN_ACTION_URL)
            .method(Connection.Method.POST)
            .data("action", "do_login")
            .data("url", "/portal.php")
            .data("username", BuildConfig.USERNAME)
            .data("password", BuildConfig.PASSWORD)
            .data("loginsubmit", "Entrar")
            .execute()

        response.loggingJSoup()

        return Jsoup.connect(url)
            .cookies(response.cookies())
            .get()
    }
}