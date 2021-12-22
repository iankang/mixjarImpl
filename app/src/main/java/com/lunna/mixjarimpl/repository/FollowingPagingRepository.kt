package com.lunna.mixjarimpl.repository

import androidx.room.withTransaction
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity
import com.lunna.mixjarimpl.db.entities.FollowingPagingEntity

class FollowingPagingRepository(
    private val db:MixjarImplDB
) {
    suspend fun insertKeys(keys:List<FollowingPagingEntity>){
        db.withTransaction {
            db.followingPagingDAO.insertPaging(keys)
        }

    }

    suspend fun getPagingByKey(key:String): FollowingPagingEntity?{
        return db.withTransaction {
            db.followingPagingDAO.getPaging(key)
        }
    }

    suspend fun deleteAll(){
        db.withTransaction {
            db.followingPagingDAO.deleteAll()
        }
    }
}