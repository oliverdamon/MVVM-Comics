package com.example.mangavinek.data.model.home.mapper

import com.example.mangavinek.data.model.home.mock.MockNewChapter
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector

class HomeMapperTest{

    @get:Rule
    val erroCollector = ErrorCollector()

    private lateinit var mockNewChapter: MockNewChapter

    @Before
    fun setUp() {
        mockNewChapter = MockNewChapter()
    }

    @Test
    fun `verifica se a lista da entidade foi transformada para domain contendo os mesmos dados`() {
        val newChapterDomainList = HomeMapper.transformEntityToDomain(mockNewChapter.mockEntityList())
        val objectPositionOne = newChapterDomainList[1]

        erroCollector.checkThat(objectPositionOne.title, `is`("Title 1"))
        erroCollector.checkThat(objectPositionOne.image, `is`("Image 1"))
        erroCollector.checkThat(objectPositionOne.url, `is`("Url 1"))

        val objectPositionTwo = newChapterDomainList[2]

        erroCollector.checkThat(objectPositionTwo.title, `is`("Title 2"))
        erroCollector.checkThat(objectPositionTwo.image, `is`("Image 2"))
        erroCollector.checkThat(objectPositionTwo.url, `is`("Url 2"))
    }
}