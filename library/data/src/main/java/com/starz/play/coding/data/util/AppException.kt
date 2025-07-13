package com.starz.play.coding.data.util

class AppException(
    override val message: String,
    val original: Throwable? = null
) : Exception(message, original)