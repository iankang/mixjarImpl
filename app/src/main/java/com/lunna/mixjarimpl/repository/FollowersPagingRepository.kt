package com.lunna.mixjarimpl.repository

import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity

class FollowersPagingRepository(
    private val db: MixjarImplDB
) {
    fun insertKeys(keys:List<FollowersPagingEntity>){
        db.followersPagingDAO.insertPaging(keys)
    }

    fun getPagingByKey(key:String):FollowersPagingEntity?{
        return db.followersPagingDAO.getPaging(key)
    }

    fun deleteAll(){
        db.followersPagingDAO.deleteAll()
    }
}