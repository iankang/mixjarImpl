package com.lunna.mixjarimpl.repository

import androidx.room.withTransaction
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.entities.FollowersPagingEntity

class FollowersPagingRepository(
    private val db: MixjarImplDB
) {
    suspend fun insertKeys(keys:List<FollowersPagingEntity>){
        db.withTransaction {
            db.followersPagingDAO.insertPaging(keys)
        }

    }

    suspend fun getPagingByKey(key:String):FollowersPagingEntity?{
        return db.withTransaction {
            db.followersPagingDAO.getPaging(key)
        }
    }

    suspend fun deleteAll(){
         db.withTransaction {
            db.followersPagingDAO.deleteAll()
        }

    }
}