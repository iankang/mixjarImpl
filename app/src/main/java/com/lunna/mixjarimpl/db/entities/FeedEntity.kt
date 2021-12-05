package com.lunna.mixjarimpl.db.entities

data class FeedEntity(
    val cloudcasts: List<Cloudcast>,
    val created_time: String,
    val from: From,
    val key: String,
    val title: String,
    val type: String,
    val url: String
)


data class Cloudcast(
    val audio_length: Int,
    val comment_count: Int,
    val created_time: String,
    val favorite_count: Int,
    val key: String,
    val listener_count: Int,
    val name: String,
    val pictures: Pictures,
    val play_count: Int,
    val repost_count: Int,
    val slug: String,
    val tags: List<Tag>,
    val updated_time: String,
    val url: String,
    val user: User
)


data class From(
    val key: String,
    val name: String,
    val pictures: PicturesXX,
    val url: String,
    val username: String
)

data class Paging(
    val next: String,
    val previous: Any
)

data class Pictures(
    val extraLarge: String,
    val large: String,
    val medium: String,
    val mediumMobile: String,
    val small: String,
    val small320wx320h: String,
    val small640wx640h: String,
    val thumbnail: String
)

data class PicturesX(
    val extraLarge: String,
    val large: String,
    val medium: String,
    val mediumMobile: String,
    val small: String,
    val small320wx320h: String,
    val small640wx640h: String,
    val thumbnail: String
)

data class PicturesXX(
    val extraLarge: String,
    val large: String,
    val medium: String,
    val mediumMobile: String,
    val small: String,
    val small320wx320h: String,
    val small640wx640h: String,
    val thumbnail: String
)

data class Tag(
    val key: String,
    val name: String,
    val url: String
)

data class User(
    val key: String,
    val name: String,
    val pictures: PicturesX,
    val url: String,
    val username: String
)