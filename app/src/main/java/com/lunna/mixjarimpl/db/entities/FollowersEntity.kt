package com.lunna.mixjarimpl.db.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is the user followers entity used in room db.
 * @author Ian Kang'ethe
 * @version 1.0
 */
@Entity
data class FollowersEntity(
    @PrimaryKey
    @NonNull
    var key:String,
    var mainUser:String?,
    var url:String?,
    var name:String?,
    var username:String?,
    var pictureUrl:String?
)
