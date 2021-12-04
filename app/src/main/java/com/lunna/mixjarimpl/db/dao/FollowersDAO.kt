package com.lunna.mixjarimpl.db.dao


import androidx.paging.PagingSource
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

    @Query("SELECT * FROM followersentity WHERE mainUser = :mainUser")
    fun getAllFollowersByMainUser(mainUser:String?):PagingSource<Int,FollowersEntity>

    @Query("SELECT * FROM followersentity WHERE username = :username")
    fun getFollowerByUsername(username:String):FollowersEntity?

//    @Query("SELECT * FROM followersentity WHERE username = :username")
//    fun getFollowerByUsernamePaged(username:String):PagingSource<Int,FollowersEntity>

    @Query("DELETE FROM followersentity")
    fun deleteAll()

    @Query("DELETE FROM followersentity WHERE mainUser = :mainUser")
    fun deleteAllFromUsername(mainUser: String)

    @Query("SELECT COUNT(*) FROM followersentity")
    fun count():Int?

    @Query("SELECT COUNT(*) FROM followersentity WHERE mainUser=:mainUser")
    fun countByMainUser(mainUser:String?):Int?
}