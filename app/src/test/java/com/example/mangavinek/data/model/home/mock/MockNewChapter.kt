package com.example.mangavinek.data.model.home.mock

import com.example.mangavinek.data.model.home.entity.NewChapterResponse

class MockNewChapter{

    fun mockEntityList(): List<NewChapterResponse> {
        val newChapterResponseList = ArrayList<NewChapterResponse>()
        for (i in 0..10) {
            newChapterResponseList.add(mockEntity(i))
        }
        return newChapterResponseList
    }

    private fun mockEntity(number: Int): NewChapterResponse {
        val newChapterResponse = NewChapterResponse()
        newChapterResponse.title = "Title $number"
        newChapterResponse.image = "Image $number"
        newChapterResponse.url = "Url $number"
        return newChapterResponse
    }
}