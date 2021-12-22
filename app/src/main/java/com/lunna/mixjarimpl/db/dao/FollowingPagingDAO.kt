package com.lunna.mixjarimpl.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity
import com.lunna.mixjarimpl.db.entities.FollowingPagingEntity

@Dao
interface FollowingPagingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaging(followersPagingEntity: List<FollowingPagingEntity>)

    @Query("SELECT * FROM following_paging WHERE `key`=:key")
    fun getPaging(key:String):FollowingPagingEntity?

    @Query("DELETE FROM following_paging")
    fun deleteAll()
}