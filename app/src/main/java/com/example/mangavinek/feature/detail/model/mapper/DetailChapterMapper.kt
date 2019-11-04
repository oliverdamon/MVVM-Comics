package com.example.mangavinek.feature.detail.model.mapper

import com.example.mangavinek.data.entity.detail.DetailChapterResponse
import com.example.mangavinek.feature.detail.model.domain.DetailChapterDomain

object DetailChapterMapper {
    fun transformEntityToDomain(listDetailChapterResponse: List<DetailChapterResponse>): List<DetailChapterDomain> {
        val listDetailChapterDomain = ArrayList<DetailChapterDomain>()

        listDetailChapterResponse.forEach {
            val detailChapterDomain = DetailChapterDomain(imageCover= it.imageChapter, url = it.urlChapter)
            listDetailChapterDomain.add(detailChapterDomain)
        }

        return listDetailChapterDomain
    }
}