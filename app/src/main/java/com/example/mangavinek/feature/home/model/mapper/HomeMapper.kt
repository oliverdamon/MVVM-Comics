package com.example.mangavinek.feature.home.model.mapper

import com.example.mangavinek.data.entity.home.NewChapterResponse
import com.example.mangavinek.feature.home.model.domain.NewChapterDomain

object HomeMapper {
    fun transformEntityToDomain(listNewChapterResponse: List<NewChapterResponse>): List<NewChapterDomain> {
        val listNewChapterDomain = ArrayList<NewChapterDomain>()

        listNewChapterResponse.forEach {
            val newChapterDomain = NewChapterDomain(title = it.title, image = it.image, url = it.url)
            listNewChapterDomain.add(newChapterDomain)
        }

        return listNewChapterDomain
    }
}