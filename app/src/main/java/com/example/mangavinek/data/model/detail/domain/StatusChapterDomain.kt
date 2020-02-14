package com.example.mangavinek.data.model.detail.domain

data class StatusChapterDomain(val status: String, val number: String) {
    companion object {
        const val AVALAIBLE = "AVAILABLE"
        const val TRANSLATED = "TRANSLATED"
        const val UNAVAILABLE = "UNAVAILABLE"
    }
}