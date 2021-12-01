package com.lunna.mixjarimpl.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lunna.mixjarimpl.db.entities.FollowingEntity

/**
 * list of following db operations
 */
@Dao
interface FollowingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFollowing(following:FollowingEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyFollowing(following:List<FollowingEntity?>)

    @Query("SELECT * FROM FOLLOWINGENTITY")
    fun getAllFollowing():List<FollowingEntity?>

    @Query("SELECT * FROM FOLLOWINGENTITY WHERE :mainUser=mainUser")
    fun getAllFollowingByMainUser(mainUser:String):List<FollowingEntity?>

    @Query("SELECT * FROM FOLLOWINGENTITY WHERE :username = username")
    fun getFollowingUser(username:String):FollowingEntity?

    @Query("DELETE FROM FOLLOWINGENTITY")
    fun deleteAll()
}