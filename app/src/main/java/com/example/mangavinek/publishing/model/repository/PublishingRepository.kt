package com.example.mangavinek.publishing.model.repository

import com.example.mangavinek.R
import com.example.mangavinek.publishing.model.domain.mock.PublishingObject

class PublishingRepository {
    private val itemList = arrayListOf<PublishingObject>()

    fun listPublishing(): List<PublishingObject>? {
        addListPublishing()
        return itemList
    }

    private fun addListPublishing() {
        itemList.add(PublishingObject("Todas", R.drawable.publishing_todas))
        itemList.add(PublishingObject("Avatar Press", R.drawable.publishing_avatar))
        itemList.add(PublishingObject("Boom! Studios", R.drawable.publishing_boom_studios))
        itemList.add(PublishingObject("Cross Overs", R.drawable.publishing_no_group))
        itemList.add(PublishingObject("Dark Horse", R.drawable.publishing_dark_horse))
        itemList.add(PublishingObject("DC Comics", R.drawable.publishing_dc_comics))
        itemList.add(PublishingObject("DC Comics (Novos 52)", R.drawable.publishing_dc_comics_news_52))
        itemList.add(PublishingObject("DC Comics (Renascimento)", R.drawable.publishing_dc_comics_rebirth))
        itemList.add(PublishingObject("DC Comics (Vertigo)", R.drawable.publishing_vertigo))
        itemList.add(PublishingObject("DC Comics (Wildstorm)", R.drawable.publishing_wildstorm))
        itemList.add(PublishingObject("Dynamite Entertainment", R.drawable.publishing_dynamite_entertaiment))
        itemList.add(PublishingObject("Fantagraphics", R.drawable.publishing_no_group))
        itemList.add(PublishingObject("Harris Comics", R.drawable.publishing_no_group))
        itemList.add(PublishingObject("IDW Publishing", R.drawable.publishing_idw))
        itemList.add(PublishingObject("Image Comics", R.drawable.publishing_image_comics))
        itemList.add(PublishingObject("Image Comics (Top Cow)", R.drawable.publishing_image_comics_v2))
        itemList.add(PublishingObject("Marvel Comics", R.drawable.publishing_marvel))
        itemList.add(PublishingObject("Marvel Comics (Icon Comics)", R.drawable.publishing_icon))
        itemList.add(PublishingObject("Marvel Comics (MAX)", R.drawable.publishing_marvel_max))
        itemList.add(PublishingObject("Marvel Comics (Ultimate)", R.drawable.publishing_marvel_ultimate))
        itemList.add(PublishingObject("Radical Comics", R.drawable.publishing_no_group))
        itemList.add(PublishingObject("Valiant", R.drawable.publishing_valiant))
        itemList.add(PublishingObject("Zenescope", R.drawable.publishing_zenescope))
        itemList.add(PublishingObject("Manga", R.drawable.publishing_mangas))
        itemList.add(PublishingObject("Outras Editoras", R.drawable.publishing_no_group))
    }
}