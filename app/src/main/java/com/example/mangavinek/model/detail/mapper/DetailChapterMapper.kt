package com.example.mangavinek.model.detail.mapper

import com.example.mangavinek.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.model.detail.domain.DetailChapterDomain

object DetailChapterMapper {
    fun transformEntityToDomain(listDetailChapterResponse: List<DetailChapterResponse>): List<DetailChapterDomain> {
        val listDetailChapterDomain = ArrayList<DetailChapterDomain>()

        listDetailChapterResponse.forEach {
            val detailChapterDomain =
                DetailChapterDomain(
                    imageCover = it.imageChapter,
                    url = it.urlChapter
                )
            listDetailChapterDomain.add(detailChapterDomain)
        }

        return listDetailChapterDomain
    }
}