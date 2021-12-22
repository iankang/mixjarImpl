package com.lunna.mixjarimpl.repository

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.entities.FollowingEntity
import com.mixsteroids.mixjar.models.UserFollowingResponseData

class FollowingRepository(
    val db:MixjarImplDB
){

    suspend fun addFollower(FollowingEntity: FollowingEntity?){
        db.withTransaction {
            db.followingDAO.insertFollowing(FollowingEntity)
        }
    }

    suspend fun addManyFollowing(FollowingEntity: List<FollowingEntity>){
        db.withTransaction {
            db.followingDAO.insertManyFollowing(FollowingEntity)
        }
    }

    suspend fun getAllFollowing(): List<FollowingEntity?> {
        return db.withTransaction {
            db.followingDAO.getAllFollowing()
        }
    }

    fun getAllFollowingByMainUser(mainUser:String): PagingSource<Int, FollowingEntity> {
           return db.followingDAO.getAllFollowingByMainUser(mainUser)
    }

    suspend fun getFollowerByUsername(username:String): FollowingEntity?{
        return db.withTransaction {
            db.followingDAO.getFollowingUser(username)
        }

    }

//    fun getFollowersByUsernamePaged(username: String): PagingSource<Int, FollowingEntity> {
//        return db.followingDAO.getFollowerByUsernamePaged(username)
//    }

    suspend fun deleteAllFollowing(){
        return db.withTransaction {
            db.followingDAO.deleteAll()
        }

    }

    suspend fun deleteAllByUserName(username: String){
        return db.withTransaction {
            db.followingDAO.deleteAllFromMainUser(username)
        }
    }

    suspend fun getTotalCount():Int?{
        return db.withTransaction {
            db.followingDAO.count()
        }
    }

    suspend fun getTotalCountByMainUser(mainUser: String):Int?{
        return db.withTransaction {
            db.followingDAO.countByMainUser(mainUser)
        }
    }

}

fun UserFollowingResponseData.toFollowingEntity(mainUser: String?): FollowingEntity? {
    return key?.let {
        FollowingEntity(
            key = it,
            mainUser = mainUser,
            url = url,
            name = name,
            username = username,
            pictureUrl = pictures?.extra_large
        )
    }
}
