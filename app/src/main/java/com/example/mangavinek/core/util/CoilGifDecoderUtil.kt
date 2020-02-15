package com.example.mangavinek.core.util

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

val gifDecoder: (Context) -> ImageLoader = { context ->
    ImageLoader(context) {
        componentRegistry {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                add(ImageDecoderDecoder())
            } else {
                add(GifDecoder())
            }
        }
    }
}