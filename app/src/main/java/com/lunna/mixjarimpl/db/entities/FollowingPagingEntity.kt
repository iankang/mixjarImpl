package com.lunna.mixjarimpl.db.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "following_paging")
data class FollowingPagingEntity(
    @PrimaryKey
    @NonNull
    val key:String,
    val previousKey:Int?,
    val nextKey:Int?
)
