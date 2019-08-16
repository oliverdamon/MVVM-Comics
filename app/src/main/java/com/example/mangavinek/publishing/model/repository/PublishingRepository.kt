package com.example.mangavinek.publishing.model.repository

import com.example.mangavinek.publishing.model.domain.entity.PublishingObject

class PublishingRepository {
    private val itemList = arrayListOf<PublishingObject>()

    fun listPublishing(): List<PublishingObject> {
        addListPublishing()
        return itemList
    }

    private fun addListPublishing() {
        itemList.add(PublishingObject("Todas"))
        itemList.add(PublishingObject("Avatar Press"))
        itemList.add(PublishingObject("Boom! Studios"))
        itemList.add(PublishingObject("Cross Overs"))
        itemList.add(PublishingObject("Dark Horse"))
        itemList.add(PublishingObject("DC Comics"))
        itemList.add(PublishingObject("DC Comics (Novos 52)"))
        itemList.add(PublishingObject("DC Comics (Renascimento)"))
        itemList.add(PublishingObject("DC Comics (Vertigo)"))
        itemList.add(PublishingObject("DC Comics (Wildstorm)"))
        itemList.add(PublishingObject("Dynamite Entertainment"))
        itemList.add(PublishingObject("Fantagraphics"))
        itemList.add(PublishingObject("Harris Comics"))
        itemList.add(PublishingObject("IDW Publishing"))
        itemList.add(PublishingObject("Image Comics"))
        itemList.add(PublishingObject("Image Comics (Top Cow)"))
        itemList.add(PublishingObject("Marvel Comics"))
        itemList.add(PublishingObject("Marvel Comics (Icon Comics)"))
        itemList.add(PublishingObject("Marvel Comics (MAX)"))
        itemList.add(PublishingObject("Marvel Comics (Ultimate)"))
        itemList.add(PublishingObject("Radical Comics"))
        itemList.add(PublishingObject("Valiant"))
        itemList.add(PublishingObject("Zenescope"))
        itemList.add(PublishingObject("Manga"))
        itemList.add(PublishingObject("Outras Editoras"))
    }
}