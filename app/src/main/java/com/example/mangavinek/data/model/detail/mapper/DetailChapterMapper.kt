package com.example.mangavinek.data.model.detail.mapper

import com.example.mangavinek.feature.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.data.model.detail.domain.DetailChapterDomain

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