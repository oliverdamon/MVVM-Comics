package com.example.mangavinek.feature.publishing.repository

import com.example.mangavinek.R
import com.example.mangavinek.feature.publishing.domain.PublishingDomain

class PublishingRepository {
    private val itemList = arrayListOf<PublishingDomain>()

    fun listPublishing(): List<PublishingDomain>? {
        addListPublishing()
        return itemList
    }

    private fun addListPublishing() {
        itemList.add(PublishingDomain("Todas", R.drawable.publishing_todas))
        itemList.add(PublishingDomain("Avatar Press", R.drawable.publishing_avatar))
        itemList.add(PublishingDomain("Boom! Studios", R.drawable.publishing_boom_studios))
        itemList.add(PublishingDomain("Cross Overs", R.drawable.publishing_no_group))
        itemList.add(PublishingDomain("Dark Horse", R.drawable.publishing_dark_horse))
        itemList.add(PublishingDomain("DC Comics", R.drawable.publishing_dc_comics))
        itemList.add(PublishingDomain("DC Comics (Novos 52)", R.drawable.publishing_dc_comics_news_52))
        itemList.add(PublishingDomain("DC Comics (Renascimento)", R.drawable.publishing_dc_comics_rebirth))
        itemList.add(PublishingDomain("DC Comics (Vertigo)", R.drawable.publishing_vertigo))
        itemList.add(PublishingDomain("DC Comics (Wildstorm)", R.drawable.publishing_wildstorm))
        itemList.add(PublishingDomain("Dynamite Entertainment", R.drawable.publishing_dynamite_entertaiment))
        itemList.add(PublishingDomain("Fantagraphics", R.drawable.publishing_no_group))
        itemList.add(PublishingDomain("Harris Comics", R.drawable.publishing_no_group))
        itemList.add(PublishingDomain("IDW Publishing", R.drawable.publishing_idw))
        itemList.add(PublishingDomain("Image Comics", R.drawable.publishing_image_comics))
        itemList.add(PublishingDomain("Image Comics (Top Cow)", R.drawable.publishing_image_comics_v2))
        itemList.add(PublishingDomain("Marvel Comics", R.drawable.publishing_marvel))
        itemList.add(PublishingDomain("Marvel Comics (Icon Comics)", R.drawable.publishing_icon))
        itemList.add(PublishingDomain("Marvel Comics (MAX)", R.drawable.publishing_marvel_max))
        itemList.add(PublishingDomain("Marvel Comics (Ultimate)", R.drawable.publishing_marvel_ultimate))
        itemList.add(PublishingDomain("Radical Comics", R.drawable.publishing_no_group))
        itemList.add(PublishingDomain("Valiant", R.drawable.publishing_valiant))
        itemList.add(PublishingDomain("Zenescope", R.drawable.publishing_zenescope))
        itemList.add(PublishingDomain("Manga", R.drawable.publishing_mangas))
        itemList.add(PublishingDomain("Outras Editoras", R.drawable.publishing_no_group))
    }
}