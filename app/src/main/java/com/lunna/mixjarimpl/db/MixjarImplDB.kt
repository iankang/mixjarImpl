package com.lunna.mixjarimpl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lunna.mixjarimpl.db.dao.*
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity
import com.lunna.mixjarimpl.db.entities.FollowingEntity
import com.lunna.mixjarimpl.db.entities.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class,
        FollowingEntity::class,
        FollowersEntity::class,
        FollowersPagingEntity::class],
    version = 4,
    exportSchema = false)
abstract class MixjarImplDB: RoomDatabase() {
    abstract val profileDAO:ProfileDAO
    abstract val followingDAO:FollowingDAO
    abstract val followingPagingDAO:FollowingPagingDAO
    abstract val followersDAO:FollowersDAO
    abstract val followersPagingDAO:FollowersPagingDAO
}