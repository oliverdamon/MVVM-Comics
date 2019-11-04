package com.example.mangavinek.feature.detail.model.mapper

import com.example.mangavinek.data.entity.detail.DetailResponse
import com.example.mangavinek.feature.detail.model.domain.DetailDomain
import com.example.mangavinek.feature.detail.model.domain.StatusChapterDomain

object DetailMapper {

    fun transformEntityToDomain(detailReponse: DetailResponse): DetailDomain? {
        detailReponse.let {
            return DetailDomain(
                title = it.title,
                titleOriginal = it.titleOriginal,
                year = it.year,
                publishing = it.publishing,
                publication = it.publication,
                status = it.status,
                sinopse = it.sinopse,
                imageCover = it.image,
                url = it.url)
        }
    }

    fun transformCommonStatusToCustomStatus(detailResponse: DetailResponse): MutableList<StatusChapterDomain> {
        val partsAvailable = detailResponse.issueAvailable.split(" ".toRegex())
        val partsTranslated = detailResponse.issueTranslated.split(" ".toRegex())
        val partsUnavailable = detailResponse.issueUnavailable.split(" ".toRegex())
        val mutableList = mutableListOf<StatusChapterDomain>()

        partsAvailable.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapterDomain(
                    StatusChapterDomain.AVALAIBLE,
                    it
                )
                mutableList.add(statusChapter)
            }
        }

        partsTranslated.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapterDomain(
                    StatusChapterDomain.TRANSLATED,
                    it
                )
                mutableList.add(statusChapter)
            }
        }

        partsUnavailable.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapterDomain(
                    StatusChapterDomain.UNAVAILABLE,
                    it
                )
                mutableList.add(statusChapter)
            }
        }

        mutableList.sortWith(compareBy(StatusChapterDomain::number))

        return mutableList
    }
}