package com.example.travelbuddy.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
sealed interface ImageType {

    @Composable
    fun resolveImage(): ImageVector
    data class Vector(val icon: ImageVector): ImageType {
        @Composable
        override fun resolveImage(): ImageVector = icon
    }

    data class Drawable(val id: Int): ImageType {
        @Composable
        override fun resolveImage(): ImageVector = ImageVector.vectorResource(id = id)
    }
}