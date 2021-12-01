package com.lunna.mixjarimpl.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity

@Dao
interface FollowersPagingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaging(followersPagingEntity: List<FollowersPagingEntity>)

    @Query("SELECT * FROM followers_paging WHERE `key`=:key")
    fun getPaging(key:String):FollowersPagingEntity?

    @Query("DELETE FROM followers_paging")
    fun deleteAll()
}