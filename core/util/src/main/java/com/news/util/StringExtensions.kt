package com.news.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.encodeForUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
fun String.decodeFromUrl(): String = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
