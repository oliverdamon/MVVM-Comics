package com.example.mangavinek.data.model.home.mapper

import com.example.mangavinek.data.model.home.entity.NewChapterResponse
import com.example.mangavinek.data.model.home.domain.NewChapterDomain

object HomeMapper {
    fun transformEntityToDomain(listNewChapterResponse: List<NewChapterResponse>): List<NewChapterDomain> {
        val listNewChapterDomain = ArrayList<NewChapterDomain>()

        listNewChapterResponse.forEach {
            val newChapterDomain = NewChapterDomain(
                title = it.title,
                image = it.image,
                url = it.url
            )
            listNewChapterDomain.add(newChapterDomain)
        }

        return listNewChapterDomain
    }
}