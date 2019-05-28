package com.example.mangavinek.entity

data class ResponseList(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)