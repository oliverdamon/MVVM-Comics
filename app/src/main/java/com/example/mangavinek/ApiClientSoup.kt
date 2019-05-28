package com.example.mangavinek

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object JSoupService {
    private const val LOGIN_ACTION_URL = "http://soquadrinhos.com/member.php"
    private const val USERNAME = "oliverdamon"
    private const val PASSWORD = "onrezzero"

    fun getApiClient(url: String): Document {
        val response = Jsoup.connect(LOGIN_ACTION_URL)
            .method(Connection.Method.POST)
            .data("action", "do_login")
            .data("url", "/portal.php")
            .data("username", USERNAME)
            .data("password", PASSWORD)
            .data("loginsubmit", "Entrar")
            .execute()

        return Jsoup.connect(url)
            .cookies(response.cookies())
            .get()
    }
}