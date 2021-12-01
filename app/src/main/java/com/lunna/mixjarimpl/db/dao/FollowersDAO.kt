package com.lunna.mixjarimpl.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lunna.mixjarimpl.db.entities.FollowersEntity

/**
 * list of followers db operations
 */
@Dao
interface FollowersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFollowers(follower:FollowersEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyFollowers(followers:List<FollowersEntity?>)

    @Query("SELECT * FROM followersentity")
    fun getAllFollowers():List<FollowersEntity?>

    @Query("SELECT * FROM followersentity WHERE :mainUser = mainUser")
    fun getAllFollowersByMainUser(mainUser:String?):List<FollowersEntity>

    @Query("SELECT * FROM followersentity WHERE :username = username")
    fun getFollowerByUsername(username:String):FollowersEntity?

    @Query("DELETE FROM followersentity")
    fun deleteAll()
}