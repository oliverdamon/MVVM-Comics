package com.example.mangavinek.data.source.remote

import com.example.mangavinek.core.constant.LOGIN_ACTION_URL
import com.example.mangavinek.core.helper.loggingInterceptorJSoup
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

object ApiClientSoup {

    private const val USERNAME = ""
    private const val PASSWORD = ""

    val requestSoup: (String) -> Document = { url ->
        try {
            val response = Jsoup.connect(LOGIN_ACTION_URL)
                .method(Connection.Method.POST)
                .data("action", "do_login")
                .data("url", "/portal.php")
                .data("username", USERNAME)
                .data("password", PASSWORD)
                .data("loginsubmit", "Entrar")
                .execute()

            response.loggingInterceptorJSoup()

            Jsoup.connect(url)
                .cookies(response.cookies())
                .get()

        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

}