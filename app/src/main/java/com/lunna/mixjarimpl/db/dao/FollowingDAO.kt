package com.lunna.mixjarimpl.db.dao

import androidx.paging.PagingSource
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

    @Query("SELECT * FROM followingentity")
    fun getAllFollowing():List<FollowingEntity?>

    @Query("SELECT * FROM followingentity WHERE mainUser = :mainUser")
    fun getAllFollowingByMainUser(mainUser:String):PagingSource<Int,FollowingEntity>

    @Query("SELECT * FROM followingentity WHERE username =:username")
    fun getFollowingUser(username:String):FollowingEntity?

    @Query("DELETE FROM followingentity")
    fun deleteAll()

    @Query("DELETE FROM followingentity WHERE mainUser=:mainUser")
    fun deleteAllFromMainUser(mainUser: String)

    @Query("SELECT COUNT(*) FROM followingentity")
    fun count():Int?

    @Query("SELECT COUNT(*) FROM followingentity WHERE mainUser=:mainUser")
    fun countByMainUser(mainUser: String):Int?
}