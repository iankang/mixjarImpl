package com.lunna.mixjarimpl.repository

import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity
import com.lunna.mixjarimpl.db.entities.FollowingPagingEntity

class FollowingPagingRepository(
    private val db:MixjarImplDB
) {
    fun insertKeys(keys:List<FollowingPagingEntity>){
        db.followingPagingDAO.insertPaging(keys)
    }

    fun getPagingByKey(key:String): FollowingPagingEntity?{
        return db.followingPagingDAO.getPaging(key)
    }

    fun deleteAll(){
        db.followingPagingDAO.deleteAll()
    }
}