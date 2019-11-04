package com.example.mangavinek.core.service

import com.example.mangavinek.BuildConfig
import com.example.mangavinek.core.helper.loggingInterceptorJSoup
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

object ApiClientSoup {

    val requestSoup: (String) -> Document = { url ->
        try {
            val response = Jsoup.connect(BuildConfig.LOGIN_ACTION_URL)
                .method(Connection.Method.POST)
                .data("action", "do_login")
                .data("url", "/portal.php")
                .data("username", BuildConfig.USERNAME)
                .data("password", BuildConfig.PASSWORD)
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