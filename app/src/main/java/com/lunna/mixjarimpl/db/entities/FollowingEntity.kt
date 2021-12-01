package com.lunna.mixjarimpl.db.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is the entity for users we are following.
 * @author Ian Kang'ethe
 * @version 1.0
 */
@Entity(tableName = "followingEntity")
data class FollowingEntity(
    @PrimaryKey
    @NonNull
    var key:String,
    var mainUser:String?,
    var url:String?,
    var name:String?,
    var username:String?,
    var pictureUrl:String?
)
