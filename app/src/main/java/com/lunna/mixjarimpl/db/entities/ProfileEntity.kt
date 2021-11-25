package com.lunna.mixjarimpl.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is the user profile entity used in room db.
 * @author Ian Kang'ethe
 * @version 1.0
 */
@Entity
data class ProfileEntity(
    @PrimaryKey
    var key:String,
    var url:String?,
    var username:String?,
    var pictureUrl: String?,
    var biog:String?,
    var createdTime:String?,
    var followerCount:Int?,
    var followingCount:Int?,
    var cloudCastCount:Int?,
    var favoriteCount:Int?,
    var listenCount:Int?,
    var is_pro: Boolean?,
    var is_premium: Boolean?,
    var city: String?,
    var country: String?,
    var type: String?
)
